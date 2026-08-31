package com.example.pcim.service.impl;

import com.example.pcim.dto.AssessmentDTO;
import com.example.pcim.dto.AssessmentQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Assessment;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.AssessmentMapper;
import com.example.pcim.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 考核评估 Service 实现类
 * 实现考核评估模块的业务逻辑，包括分页查询、CRUD、月度汇总等
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {

    /** 注入考核评估 Mapper */
    @Autowired
    private AssessmentMapper assessmentMapper;

    @Override
    public PageResult<Assessment> getAssessmentList(AssessmentQueryDTO queryDTO) {
        // 处理分页参数，默认第1页、每页10条
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全部符合条件的记录（由 Mapper 排序好）
        List<Assessment> list = assessmentMapper.selectList(
                queryDTO.getKeyword(), queryDTO.getType(), queryDTO.getDate());
        // 查询总记录数
        Long total = assessmentMapper.selectCount(
                queryDTO.getKeyword(), queryDTO.getType(), queryDTO.getDate());

        // 内存分页（与 PrisonerServiceImpl 保持一致）
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Assessment> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public Assessment getAssessmentById(Long id) {
        return assessmentMapper.selectById(id);
    }

    @Override
    public int addAssessment(AssessmentDTO assessmentDTO) {
        // 将 DTO 转换为 Entity
        Assessment assessment = convertDtoToEntity(assessmentDTO);
        return assessmentMapper.insert(assessment);
    }

    @Override
    public int updateAssessment(AssessmentDTO assessmentDTO) {
        // 更新前先校验记录是否存在
        Assessment existing = assessmentMapper.selectById(assessmentDTO.getId());
        if (existing == null) {
            throw new BusinessException("考核评估记录不存在");
        }

        Assessment assessment = convertDtoToEntity(assessmentDTO);
        return assessmentMapper.update(assessment);
    }

    @Override
    public int deleteAssessment(Long id) {
        // 删除前先校验记录是否存在
        Assessment existing = assessmentMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("考核评估记录不存在");
        }
        return assessmentMapper.deleteById(id);
    }

    /**
     * DTO 转 Entity 的私有工具方法
     *
     * @param dto 数据传输对象
     * @return 考核评估实体
     */
    private Assessment convertDtoToEntity(AssessmentDTO dto) {
        Assessment assessment = new Assessment();
        assessment.setId(dto.getId());
        assessment.setPrisonerNo(dto.getPrisonerNo());
        assessment.setName(dto.getName());
        assessment.setCell(dto.getCell());
        assessment.setAssessDate(dto.getAssessDate());
        assessment.setType(dto.getType());
        assessment.setItem(dto.getItem());
        assessment.setScore(dto.getScore());
        assessment.setLevel(dto.getLevel());
        assessment.setHandler(dto.getHandler());
        assessment.setRemark(dto.getRemark());
        return assessment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String runMonthlySummary() {
        // 计算上一年上月（即当前月份的上一个月）
        LocalDate today = LocalDate.now();
        YearMonth lastMonth = YearMonth.from(today).minusMonths(1);
        // 年月字符串，格式 yyyy-MM，如 "2026-07"
        String yearMonth = lastMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        // 上月的最后一天（作为汇总记录的 assessDate）
        LocalDate lastDayOfLastMonth = lastMonth.atEndOfMonth();
        Date lastDayDate = Date.from(lastDayOfLastMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 1. 先清理该月旧的月度汇总记录，保证同月重复执行不会产生重复数据
        assessmentMapper.deleteSummaryByMonth(yearMonth);

        // 2. 查询上一个月所有非汇总的考核记录（type != '月度汇总'）
        //    通过调用 selectList 传入 date 参数筛选上月数据
        List<Assessment> lastMonthRecords = assessmentMapper.selectList(null, null, yearMonth);

        // 过滤掉已经是"月度汇总"类型的记录（双重保险）
        List<Assessment> realRecords = lastMonthRecords.stream()
                .filter(a -> !"月度汇总".equals(a.getType()))
                .collect(Collectors.toList());

        if (realRecords.isEmpty()) {
            return "上月(" + yearMonth + ")无考核数据，未生成汇总记录";
        }

        // 3. 按 prisonerNo 分组，计算每个监犯的总分
        Map<String, List<Assessment>> groupedByPrisoner = realRecords.stream()
                .collect(Collectors.groupingBy(Assessment::getPrisonerNo));

        // 4. 构造汇总记录列表
        List<Assessment> summaryList = new ArrayList<>();
        for (Map.Entry<String, List<Assessment>> entry : groupedByPrisoner.entrySet()) {
            String prisonerNo = entry.getKey();
            List<Assessment> records = entry.getValue();

            // 计算分值总和
            int totalScore = records.stream()
                    .mapToInt(a -> a.getScore() != null ? a.getScore() : 0)
                    .sum();

            // 取第一条记录的姓名、监室作为汇总记录的参考字段
            Assessment first = records.get(0);

            Assessment summary = new Assessment();
            summary.setPrisonerNo(prisonerNo);
            summary.setName(first.getName());
            summary.setCell(first.getCell());
            summary.setAssessDate(lastDayDate);
            summary.setType("月度汇总");
            summary.setItem("月度(" + yearMonth + ")分值汇总");
            summary.setScore(totalScore);
            // 根据总分计算等级：>=90 优秀，>=80 良好，>=60 及格，<60 待加强
            summary.setLevel(calcLevel(totalScore));
            summary.setHandler("系统自动汇总");
            summary.setRemark("系统自动生成的" + yearMonth + "月度分值汇总");

            summaryList.add(summary);
        }

        // 5. 批量插入汇总记录
        assessmentMapper.batchInsert(summaryList);

        return "月度汇总成功：汇总月份 " + yearMonth + "，合计 " + summaryList.size() + " 名监犯";
    }

    /**
     * 根据总分计算考核等级的私有工具方法
     *
     * @param totalScore 总分
     * @return 等级字符串
     */
    private String calcLevel(int totalScore) {
        if (totalScore >= 90) {
            return "优秀";
        } else if (totalScore >= 80) {
            return "良好";
        } else if (totalScore >= 60) {
            return "及格";
        } else {
            return "待加强";
        }
    }
}
