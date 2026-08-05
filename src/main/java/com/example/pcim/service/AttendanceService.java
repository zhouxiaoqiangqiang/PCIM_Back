package com.example.pcim.service;

import com.example.pcim.dto.AttendanceDTO;
import com.example.pcim.dto.AttendanceQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Attendance;

public interface AttendanceService {
    PageResult<Attendance> getAttendanceList(AttendanceQueryDTO queryDTO);

    Attendance getAttendanceById(Long id);

    int addAttendance(AttendanceDTO attendanceDTO);

    int updateAttendance(AttendanceDTO attendanceDTO);

    int deleteAttendance(Long id);
}
