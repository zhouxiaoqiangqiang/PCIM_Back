package com.example.pcim.controller;

import com.example.pcim.dto.AssessmentDTO;
import com.example.pcim.dto.AssessmentQueryDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.entity.Assessment;
import com.example.pcim.service.AssessmentService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 考核评估 Controller
 * 处理考核评估模块的 HTTP 请求，路径前缀 /api/assessments
 */
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    /** 注入考核评估 Service */
    @Autowired
    private AssessmentService assessmentService;

    /**
     * 分页查询考核评估列表
     * 请求方式：POST
     * 请求体：AssessmentQueryDTO JSON
     *
     * @param queryDTO 分页查询条件
     * @return 分页结果（列表 + 总条数）
     */
    @PostMapping("/list")
    public Response<?> getAssessmentList(@RequestBody AssessmentQueryDTO queryDTO) {
        return Response.success(assessmentService.getAssessmentList(queryDTO));
    }

    /**
     * 根据ID查询单条考核评估记录
     * 请求方式：GET
     * 路径参数：id - 考核记录主键ID
     *
     * @param id 主键ID
     * @return 考核评估实体
     */
    @GetMapping("/{id}")
    public Response<?> getAssessmentById(@PathVariable Long id) {
        return Response.success(assessmentService.getAssessmentById(id));
    }

    /**
     * 新增一条考核评估记录
     * 请求方式：POST
     * 请求体：AssessmentDTO JSON
     *
     * @param assessmentDTO 考核评估数据
     * @return 操作提示
     */
    @PostMapping
    public Response<?> addAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        assessmentService.addAssessment(assessmentDTO);
        return Response.success("新增成功");
    }

    /**
     * 更新一条考核评估记录
     * 请求方式：PUT
     * 请求体：AssessmentDTO JSON（必须包含 id 字段）
     *
     * @param assessmentDTO 考核评估数据
     * @return 操作提示
     */
    @PutMapping
    public Response<?> updateAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        assessmentService.updateAssessment(assessmentDTO);
        return Response.success("修改成功");
    }

    /**
     * 根据ID删除一条考核评估记录
     * 请求方式：DELETE
     * 路径参数：id - 考核记录主键ID
     *
     * @param id 主键ID
     * @return 操作提示
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteAssessment(@PathVariable Long id) {
        assessmentService.deleteAssessment(id);
        return Response.success("删除成功");
    }

    /**
     * 执行月度汇总
     * 请求方式：POST
     * 路径：/api/assessments/monthly-summary
     * 业务逻辑：
     *   1. 计算上一年上月
     *   2. 清理该月旧的汇总记录
     *   3. 查询上月所有非汇总考核记录，按监犯编号分组计算总分
     *   4. 生成 type='月度汇总' 的新记录并批量插入
     *
     * @return 操作结果提示（包含汇总月份和生成条数）
     */
    @PostMapping("/monthly-summary")
    public Response<?> runMonthlySummary() {
        String result = assessmentService.runMonthlySummary();
        return Response.success(result);
    }


    /**
     * 导出考核记录为 Excel（xlsx）
     * 前端 responseType: 'blob'，直接返回文件流
     */
    @PostMapping("/export")
    public void exportAssessments(@RequestBody AssessmentQueryDTO queryDTO, HttpServletResponse response) throws Exception {
        // 查询符合条件的全部记录（不分页）
        List<Assessment> list = assessmentService.getAssessmentListForExport(queryDTO);

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = URLEncoder.encode("考核记录.xlsx", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 使用 Apache POI 生成 xlsx
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("考核记录");

        // 表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // 写表头
        String[] headers = {"监犯编号", "姓名", "监区", "考核日期", "考核类型", "考核事项", "分值", "等级", "经办人", "备注"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // 写数据行
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < list.size(); i++) {
            Assessment a = list.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(a.getPrisonerNo() != null ? a.getPrisonerNo() : "");
            row.createCell(1).setCellValue(a.getName() != null ? a.getName() : "");
            row.createCell(2).setCellValue(a.getCell() != null ? a.getCell() : "");
            row.createCell(3).setCellValue(a.getAssessDate() != null ? sdf.format(a.getAssessDate()) : "");
            row.createCell(4).setCellValue(a.getType() != null ? a.getType() : "");
            row.createCell(5).setCellValue(a.getItem() != null ? a.getItem() : "");
            row.createCell(6).setCellValue(a.getScore() != null ? a.getScore() : 0);
            row.createCell(7).setCellValue(a.getLevel() != null ? a.getLevel() : "");
            row.createCell(8).setCellValue(a.getHandler() != null ? a.getHandler() : "");
            row.createCell(9).setCellValue(a.getRemark() != null ? a.getRemark() : "");
        }

        // 自动列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 输出到响应流
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();
    }
}
