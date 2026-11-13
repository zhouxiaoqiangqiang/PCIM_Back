package com.example.pcim.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Jackson 全局日期配置
 * <p>
 * 问题背景：
 * 序列化时统一使用 yyyy-MM-dd HH:mm:ss，但反序列化如果也按该格式严格解析，
 * 前端日期选择器传来的纯日期字符串（如 "2026-11-13"）就会报
 * "Cannot deserialize value of type java.util.Date from String ..."。
 * <p>
 * 处理方案：
 * 1. 序列化（后端 -> 前端）：仍由 application.yml 中的 spring.jackson.date-format 控制，
 *    统一输出 yyyy-MM-dd HH:mm:ss；
 * 2. 反序列化（前端 -> 后端）：使用本类的宽松日期反序列化器，
 *    同时兼容 "yyyy-MM-dd"、"yyyy-MM-dd HH:mm:ss"、ISO-8601 等常见格式。
 */
@Configuration
public class JacksonConfig {

    /** 统一的时区，与数据库连接参数 serverTimezone=Asia/Shanghai 保持一致 */
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+8");

    /**
     * 注册全局日期反序列化器，覆盖 Spring Boot 默认的严格日期解析
     *
     * @return Jackson 定制器
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer flexibleDateCustomizer() {
        return builder -> {
            SimpleModule module = new SimpleModule();
            // 全局替换 java.util.Date 的反序列化逻辑
            module.addDeserializer(Date.class, new FlexibleDateDeserializer());
            builder.modules(module);
        };
    }

    /**
     * 宽松日期反序列化器
     * 按顺序尝试多种常见格式，任意一种解析成功即返回
     */
    public static class FlexibleDateDeserializer extends JsonDeserializer<Date> {

        /** 支持的日期格式，日期时间格式放在纯日期格式之前，避免被部分匹配 */
        private static final String[] PATTERNS = {
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy/MM/dd HH:mm:ss",
                "yyyy-MM-dd",
                "yyyy/MM/dd"
        };

        @Override
        public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String text = parser.getText();
            // 空值或空字符串按 null 处理，避免前端清空日期时报错
            if (text == null || text.trim().isEmpty()) {
                return null;
            }
            text = text.trim();

            // 时间戳格式（纯数字）直接按毫秒值处理
            if (text.matches("\\d+")) {
                return new Date(Long.parseLong(text));
            }

            for (String pattern : PATTERNS) {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setTimeZone(TIME_ZONE);
                // 严格解析，防止 "yyyy-MM-dd" 把 "2026-11-13 09:30:00" 截断成日期
                sdf.setLenient(false);
                try {
                    return sdf.parse(text);
                } catch (ParseException ignored) {
                    // 当前格式不匹配，继续尝试下一个
                }
            }

            throw new IOException("无法解析日期字符串: " + text,
                    context.weirdStringException(text, Date.class, "支持的格式为 yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss"));
        }
    }
}
