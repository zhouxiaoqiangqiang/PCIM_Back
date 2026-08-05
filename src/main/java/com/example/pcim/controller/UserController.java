package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.UserDTO;
import com.example.pcim.dto.UserQueryDTO;
import com.example.pcim.entity.User;
import com.example.pcim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public Response<?> getUserList(@RequestBody UserQueryDTO queryDTO) {
        return Response.success(userService.getUserList(queryDTO));
    }

    @GetMapping("/{id}")
    public Response<?> getUserById(@PathVariable Long id) {
        return Response.success(userService.getUserById(id));
    }

    @PostMapping
    public Response<?> addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return Response.success("新增成功");
    }

    @PutMapping
    public Response<?> updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return Response.success("修改成功");
    }

    @DeleteMapping("/{id}")
    public Response<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Response.success("删除成功");
    }

    @PostMapping("/{id}/reset-password")
    public Response<?> resetPassword(@PathVariable Long id) {
        userService.resetPassword(id);
        return Response.success("密码已重置为123456");
    }

    @PostMapping("/{id}/status")
    public Response<?> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.changeStatus(id, status);
        return Response.success(status == 1 ? "已启用" : "已禁用");
    }
}
