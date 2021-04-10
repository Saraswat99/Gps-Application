package com.vehicle.app.controller;

import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.UserDTO;
import com.vehicle.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/client/v1/")
@PreAuthorize("hasRole('ROLE_CLIENT')")
public class ClientController {

    @Autowired
    protected UserService userService;

    @PostMapping(value = "/createTransporter")
    public ApiResponse<UserDTO> createTransporter(@RequestBody UserDTO userDTO, Authentication authentication) {
        log.info("Role list - {}", authentication.getAuthorities());
        log.info(userDTO.toString());
        return new ApiResponse<>(userService.save(authentication, userDTO));
    }

    @PutMapping(value = "/updateTransporter")
    public ApiResponse<UserDTO> updateTransporter(@RequestBody UserDTO userDTO, Authentication authentication) {
        log.info(userDTO.toString());
        return new ApiResponse<>(userService.update(authentication, userDTO));
    }

    @GetMapping(value = "/listTransporter/{userId}")
    public ApiResponse<UserDTO> listTransporter(@PathVariable Long userId) {
        return new ApiResponse<>(userService.list(userId));
    }

    @GetMapping(value = "/listTransporter")
    public ApiResponse<List<UserDTO>> listTransporter(Authentication authentication) {
        return new ApiResponse<>(userService.listAll(authentication));
    }

    @DeleteMapping(value = "/deleteTransporter/{id}")
    public ApiResponse<String> deleteTransporter(@PathVariable Long id) {
        userService.delete(id);
        return new ApiResponse<>("User Deleted");
    }
}