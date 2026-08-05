package com.example.pcim.controller;

import com.example.pcim.dto.LoginDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.entity.User;
import com.example.pcim.service.UserService;
import com.example.pcim.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Response<?> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO.getUsername(), loginDTO.getPassword());

        String token = jwtUtil.generateToken(user);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);

        return Response.success(result);
    }

    @GetMapping("/logout")
    public Response<?> logout() {
        return Response.success("退出成功");
    }
}
