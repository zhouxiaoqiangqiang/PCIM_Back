package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.WarningDTO;
import com.example.pcim.dto.WarningQueryDTO;
import com.example.pcim.entity.Prisoner;
import com.example.pcim.entity.Warning;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.PrisonerMapper;
import com.example.pcim.mapper.WarningMapper;
import com.example.pcim.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 预警信息 Service 实现类
 * 实现预警信息的核心业务逻辑
 */
@Service
public class WarningServiceImpl implements WarningService {

    @Autowired
    private WarningMapper warningMapper;

    /**
     * 犯人 Mapper，用于 checkWarnings 时获取在押犯人列表
     */
    @Autowired
    private PrisonerMapper prisonerMapper;

    @Override
    public PageResult<Warning> getWarningList(WarningQueryDTO queryDTO) {
        // 设置默认分页参数
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全量数据（后续内存分页，与现有 PrisonerServiceImpl 保持一致）
        List<Warning> list = warningMapper.selectList(
                queryDTO.getKeyword(), queryDTO.getType(),
                queryDTO.getLevel(), queryDTO.getStatus());
        Long total = warningMapper.selectCount(
                queryDTO.getKeyword(), queryDTO.getType(),
                queryDTO.getLevel(), queryDTO.getStatus());

        // 内存分页切割
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Warning> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public Warning getWarningById(Long id) {
        Warning warning = warningMapper.selectById(id);
        if (warning == null) {
            throw new BusinessException("预警记录不存在");
        }
        return warning;
    }

    @Override
    public int addWarning(WarningDTO warningDTO) {
        Warning warning = buildWarningFromDTO(warningDTO);

        // 自动生成预警编号
        warning.setWarningNo(generateWarningNo());

        // 设置默认值
        if (warning.getStatus() == null || warning.getStatus().isEmpty()) {
            warning.setStatus("待处理");
        }
        if (warning.getWarningTime() == null) {
            warning.setWarningTime(new Date());
        }
        if (warning.getIsRead() == null) {
            warning.setIsRead(0); // 新增预警默认为未读
        }
        if (warning.getSource() == null || warning.getSource().isEmpty()) {
            warning.setSource("人工上报");
        }

        return warningMapper.insert(warning);
    }

    @Override
    public int updateWarning(WarningDTO warningDTO) {
        if (warningDTO.getId() == null) {
            throw new BusinessException("预警ID不能为空");
        }
        Warning existing = warningMapper.selectById(warningDTO.getId());
        if (existing == null) {
            throw new BusinessException("预警记录不存在");
        }

        Warning warning = new Warning();
        warning.setId(warningDTO.getId());
        warning.setPrisonerNo(warningDTO.getPrisonerNo());
        warning.setName(warningDTO.getName());
        warning.setCell(warningDTO.getCell());
        warning.setType(warningDTO.getType());
        warning.setLevel(warningDTO.getLevel());
        warning.setContent(warningDTO.getContent());
        warning.setSource(warningDTO.getSource());
        warning.setStatus(warningDTO.getStatus());
        warning.setHandler(warningDTO.getHandler());
        warning.setHandleResult(warningDTO.getHandleResult());

        return warningMapper.update(warning);
    }

    @Override
    public String handleWarning(Long id, WarningDTO dto) {
        Warning existing = warningMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("预警记录不存在");
        }

        // 构建更新对象：更新状态、处理人、处理时间、处理结果
        Warning warning = new Warning();
        warning.setId(id);
        warning.setStatus(dto.getStatus() != null ? dto.getStatus() : "已处理");
        warning.setHandler(dto.getHandler() != null ? dto.getHandler() : "系统");
        warning.setHandleTime(new Date());
        warning.setHandleResult(dto.getHandleResult());
        // 处理预警时自动标记为已读
        warning.setIsRead(1);

        warningMapper.update(warning);
        return "预警处理成功";
    }

    @Override
    public int deleteWarning(Long id) {
        Warning existing = warningMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("预警记录不存在");
        }
        return warningMapper.deleteById(id);
    }

    @Override
    public String checkWarnings() {
        // 模拟策略检查：获取所有在押犯人，随机生成预警
        // 实际生产可替换为真实规则引擎（如 Drools、Easy Rules）

        // 查询所有状态为"在押"的犯人
        List<Prisoner> prisoners = prisonerMapper.selectList(null, null, "在押");
        if (prisoners == null || prisoners.isEmpty()) {
            return "没有在押犯人，无需检查";
        }

        Random random = new Random();
        int generatedCount = 0;

        // 预警类型池
        String[] types = {"行为异常", "情绪波动", "健康异常", "违规操作", "异常聚集"};
        // 预警级别池
        String[] levels = {"低", "中", "高"};
        // 预警来源
        String source = "系统检测";
        // 预警内容模板
        String[] contents = {
                "检测到犯人行为异常波动，活动频率超出正常范围",
                "情绪指标出现异常变化，建议关注",
                "健康监测数据出现异常值",
                "发现违规操作行为",
                "监室内出现异常聚集情况"
        };

        // 随机选取 1~3 个犯人生成预警
        int checkCount = Math.min(prisoners.size(), 1 + random.nextInt(3));
        for (int i = 0; i < checkCount; i++) {
            Prisoner p = prisoners.get(random.nextInt(prisoners.size()));

            Warning warning = new Warning();
            warning.setWarningNo(generateWarningNo());
            warning.setPrisonerNo(p.getPrisonerNo());
            warning.setName(p.getName());
            warning.setCell(p.getCell());
            warning.setType(types[random.nextInt(types.length)]);
            warning.setLevel(levels[random.nextInt(levels.length)]);
            warning.setWarningTime(new Date());
            warning.setContent(contents[random.nextInt(contents.length)]);
            warning.setStatus("待处理");
            warning.setSource(source);
            warning.setIsRead(0);

            warningMapper.insert(warning);
            generatedCount++;
        }

        return "预警检查完成，共生成 " + generatedCount + " 条预警";
    }

    @Override
    public Object getUnreadWarnings() {
        Map<String, Object> result = new LinkedHashMap<>();
        Long unreadCount = warningMapper.selectUnreadCount();
        List<Warning> list = warningMapper.selectUnreadList();
        result.put("unreadCount", unreadCount != null ? unreadCount : 0L);
        result.put("list", list != null ? list : Collections.emptyList());
        return result;
    }

    @Override
    public String markWarningRead(Long id) {
        Warning existing = warningMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("预警记录不存在");
        }
        warningMapper.markReadById(id);
        return "已标记为已读";
    }

    @Override
    public String markAllWarningsRead() {
        int count = warningMapper.markAllRead();
        return "已将 " + count + " 条预警标记为已读";
    }

    // ====================== 私有辅助方法 ======================

    /**
     * 从 WarningDTO 构建 Warning 实体（仅拷贝非空字段）
     */
    private Warning buildWarningFromDTO(WarningDTO dto) {
        Warning warning = new Warning();
        warning.setId(dto.getId());
        warning.setPrisonerNo(dto.getPrisonerNo());
        warning.setName(dto.getName());
        warning.setCell(dto.getCell());
        warning.setType(dto.getType());
        warning.setLevel(dto.getLevel());
        warning.setContent(dto.getContent());
        warning.setStatus(dto.getStatus());
        warning.setSource(dto.getSource());
        warning.setHandler(dto.getHandler());
        warning.setHandleResult(dto.getHandleResult());
        return warning;
    }

    /**
     * 自动生成预警编号
     * 格式: YJ + yyyyMMdd + 4位随机数字
     * 示例: YJ202608311234
     */
    private String generateWarningNo() {
        // 日期部分
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datePart = sdf.format(new Date());

        // 4位随机数字（不足4位前补0）
        Random random = new Random();
        String randomPart = String.format("%04d", random.nextInt(10000));

        return "YJ" + datePart + randomPart;
    }
}
