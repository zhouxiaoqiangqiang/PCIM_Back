package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.ReleaseDTO;
import com.example.pcim.dto.ReleaseQueryDTO;
import com.example.pcim.entity.Release;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.ReleaseMapper;
import com.example.pcim.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 离监申请 Service 实现类
 * 提供离监申请的业务逻辑处理，包括：
 * - 列表查询（内存分页）
 * - 新增申请（自动生成申请编号）
 * - 审批通过/驳回（乐观锁校验）
 * - 删除
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {

    /**
     * 乐观锁校验失败提示信息
     */
    private static final String OPTIMISTIC_LOCK_ERROR = "该申请已被处理，请刷新页面后重试";

    /**
     * 申请编号前缀
     */
    private static final String APPLY_NO_PREFIX = "LQ";

    /**
     * 注入离监申请 Mapper
     */
    @Autowired
    private ReleaseMapper releaseMapper;

    /**
     * 分页查询离监申请列表
     * 使用内存分页方式（先查全量再 subList 切片）
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果
     */
    @Override
    public PageResult<Release> getReleaseList(ReleaseQueryDTO queryDTO) {
        // 设置默认分页参数
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全量数据和总数
        List<Release> list = releaseMapper.selectList(queryDTO.getKeyword(), queryDTO.getStatus(), queryDTO.getType());
        Long total = releaseMapper.selectCount(queryDTO.getKeyword(), queryDTO.getStatus(), queryDTO.getType());

        // 内存分页切片
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Release> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    /**
     * 分页查询待审批列表
     * 仅返回状态为 PENDING 的记录
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果
     */
    @Override
    public PageResult<Release> getTodoList(ReleaseQueryDTO queryDTO) {
        // 设置默认分页参数
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询所有 PENDING 状态的记录
        List<Release> list = releaseMapper.selectTodoList();
        Long total = releaseMapper.selectTodoCount();

        // 内存分页切片
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Release> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    /**
     * 根据ID查询离监申请详情
     *
     * @param id 主键ID
     * @return 离监申请实体
     */
    @Override
    public Release getReleaseById(Long id) {
        Release release = releaseMapper.selectById(id);
        if (release == null) {
            throw new BusinessException("离监申请不存在");
        }
        return release;
    }

    /**
     * 新增离监申请
     * 自动生成申请编号，初始状态为 PENDING
     *
     * @param dto 离监申请数据
     * @return 影响行数
     */
    @Override
    public int addRelease(ReleaseDTO dto) {
        Release release = new Release();

        // 自动生成申请编号：LQ + 年月日 + 4位随机数
        release.setApplyNo(generateApplyNo());

        // 设置申请信息
        release.setPrisonerNo(dto.getPrisonerNo());
        release.setName(dto.getName());
        release.setCell(dto.getCell());
        release.setType(dto.getType());
        release.setReason(dto.getReason());

        // 初始状态为待审批
        release.setStatus("PENDING");

        // 乐观锁版本号初始为 0
        release.setVersion(0);

        // 记录申请时间
        release.setApplyTime(new Date());

        return releaseMapper.insert(release);
    }

    /**
     * 审批通过离监申请
     * 使用乐观锁校验：前端传入的 version 必须与数据库当前 version 一致
     * 否则说明已有其他操作先处理了该申请，拒绝本次操作
     *
     * @param id  申请ID
     * @param dto 含版本号和审批人信息
     * @return 操作结果消息
     */
    @Override
    public String approveRelease(Long id, ReleaseDTO dto) {
        // 1. 查询当前申请记录，校验存在性
        Release release = releaseMapper.selectById(id);
        if (release == null) {
            throw new BusinessException("离监申请不存在");
        }

        // 2. 校验当前状态是否允许审批（必须是 PENDING）
        if (!"PENDING".equals(release.getStatus())) {
            throw new BusinessException("该申请已被处理，无法重复审批");
        }

        // 3. 乐观锁版本号校验：前端传入的 version 与数据库当前 version 必须一致
        if (dto.getVersion() == null || !dto.getVersion().equals(release.getVersion())) {
            throw new BusinessException(OPTIMISTIC_LOCK_ERROR);
        }

        // 4. 更新为已通过状态
        release.setStatus("APPROVED");
        release.setApprover(dto.getApprover());
        release.setApproveTime(new Date());
        // 乐观锁：版本号 +1
        release.setVersion(release.getVersion() + 1);

        releaseMapper.update(release);

        return "审批通过";
    }

    /**
     * 审批驳回离监申请
     * 使用乐观锁校验：前端传入的 version 必须与数据库当前 version 一致
     * 否则说明已有其他操作先处理了该申请，拒绝本次操作
     *
     * @param id  申请ID
     * @param dto 含版本号和驳回原因
     * @return 操作结果消息
     */
    @Override
    public String rejectRelease(Long id, ReleaseDTO dto) {
        // 1. 查询当前申请记录，校验存在性
        Release release = releaseMapper.selectById(id);
        if (release == null) {
            throw new BusinessException("离监申请不存在");
        }

        // 2. 校验当前状态是否允许驳回（必须是 PENDING）
        if (!"PENDING".equals(release.getStatus())) {
            throw new BusinessException("该申请已被处理，无法重复驳回");
        }

        // 3. 乐观锁版本号校验：前端传入的 version 与数据库当前 version 必须一致
        if (dto.getVersion() == null || !dto.getVersion().equals(release.getVersion())) {
            throw new BusinessException(OPTIMISTIC_LOCK_ERROR);
        }

        // 4. 校验驳回原因不能为空
        if (dto.getRejectReason() == null || dto.getRejectReason().trim().isEmpty()) {
            throw new BusinessException("驳回原因不能为空");
        }

        // 5. 更新为已驳回状态
        release.setStatus("REJECTED");
        release.setRejectReason(dto.getRejectReason());
        release.setApprover(dto.getApprover());
        release.setApproveTime(new Date());
        // 乐观锁：版本号 +1
        release.setVersion(release.getVersion() + 1);

        releaseMapper.update(release);

        return "已驳回";
    }

    /**
     * 删除离监申请
     *
     * @param id 主键ID
     * @return 影响行数
     */
    @Override
    public int deleteRelease(Long id) {
        // 校验记录存在性
        Release release = releaseMapper.selectById(id);
        if (release == null) {
            throw new BusinessException("离监申请不存在");
        }
        return releaseMapper.deleteById(id);
    }

    /**
     * 自动生成申请编号
     * 格式：LQ + yyyyMMdd + 4位随机数
     * 例如：LQ202608311234
     *
     * @return 生成的申请编号
     */
    private String generateApplyNo() {
        // 日期部分：yyyyMMdd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String datePart = sdf.format(new Date());

        // 随机数部分：4位随机数（不足补0）
        Random random = new Random();
        int num = random.nextInt(10000);
        String randomPart = String.format("%04d", num);

        return APPLY_NO_PREFIX + datePart + randomPart;
    }
}
