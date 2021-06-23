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
@RequestMapping("/admin/v1/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    protected UserService userService;

    @PostMapping(value = "/createClient")
    public ApiResponse<UserDTO> createClient(@RequestBody UserDTO userDTO, Authentication authentication) {
        log.info("Role list - {}", authentication.getAuthorities());
        log.info(userDTO.toString());
        return new ApiResponse<>(userService.save(authentication, userDTO));
    }

    @PutMapping(value = "/updateClient")
    public ApiResponse<UserDTO> updateClient(@RequestBody UserDTO userDTO, Authentication authentication) {
        log.info(userDTO.toString());
        return new ApiResponse<>(userService.update(authentication, userDTO));
    }

    @GetMapping(value = "/listClient/{userId}")
    public ApiResponse<UserDTO> listClient(@PathVariable String userId) {
        return new ApiResponse<>(userService.list(userId));
    }

    @GetMapping(value = "/listClient")
    public ApiResponse<List<UserDTO>> listClient(Authentication authentication) {
        return new ApiResponse<>(userService.listAll(authentication));
    }

    @DeleteMapping(value = "/deleteClient/{id}")
    public ApiResponse<String> deleteClient(@PathVariable String id) {
        userService.delete(id);
        return new ApiResponse<>("User Deleted");
    }
}