package com.example.pcim.controller;

import com.example.pcim.dto.AttendanceDTO;
import com.example.pcim.dto.AttendanceQueryDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/list")
    public Response<?> getAttendanceList(@RequestBody AttendanceQueryDTO queryDTO) {
        return Response.success(attendanceService.getAttendanceList(queryDTO));
    }

    @GetMapping("/{id}")
    public Response<?> getAttendanceById(@PathVariable Long id) {
        return Response.success(attendanceService.getAttendanceById(id));
    }

    @PostMapping
    public Response<?> addAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        attendanceService.addAttendance(attendanceDTO);
        return Response.success("新增成功");
    }

    @PutMapping
    public Response<?> updateAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        attendanceService.updateAttendance(attendanceDTO);
        return Response.success("修改成功");
    }

    @DeleteMapping("/{id}")
    public Response<?> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return Response.success("删除成功");
    }
}
