package com.example.pcim.service.impl;

import com.example.pcim.dto.AttendanceDTO;
import com.example.pcim.dto.AttendanceQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Attendance;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.AttendanceMapper;
import com.example.pcim.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public PageResult<Attendance> getAttendanceList(AttendanceQueryDTO queryDTO) {
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        List<Attendance> list = attendanceMapper.selectList(queryDTO.getKeyword(), queryDTO.getCell(), queryDTO.getStatus());
        Long total = attendanceMapper.selectCount(queryDTO.getKeyword(), queryDTO.getCell(), queryDTO.getStatus());

        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Attendance> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public Attendance getAttendanceById(Long id) {
        return attendanceMapper.selectById(id);
    }

    @Override
    public int addAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = new Attendance();
        attendance.setPrisonerId(attendanceDTO.getPrisonerId());
        attendance.setPrisonerNo(attendanceDTO.getPrisonerNo());
        attendance.setName(attendanceDTO.getName());
        attendance.setCell(attendanceDTO.getCell());
        attendance.setRollCallTime(attendanceDTO.getRollCallTime());
        attendance.setStatus(attendanceDTO.getStatus() != null ? attendanceDTO.getStatus() : "正常");
        attendance.setCheckInTime(attendanceDTO.getCheckInTime());
        attendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
        attendance.setRemark(attendanceDTO.getRemark());

        return attendanceMapper.insert(attendance);
    }

    @Override
    public int updateAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceMapper.selectById(attendanceDTO.getId());
        if (attendance == null) {
            throw new BusinessException("出勤记录不存在");
        }

        attendance.setRollCallTime(attendanceDTO.getRollCallTime());
        attendance.setStatus(attendanceDTO.getStatus());
        attendance.setCheckInTime(attendanceDTO.getCheckInTime());
        attendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
        attendance.setRemark(attendanceDTO.getRemark());

        return attendanceMapper.update(attendance);
    }

    @Override
    public int deleteAttendance(Long id) {
        Attendance attendance = attendanceMapper.selectById(id);
        if (attendance == null) {
            throw new BusinessException("出勤记录不存在");
        }
        return attendanceMapper.deleteById(id);
    }
}
