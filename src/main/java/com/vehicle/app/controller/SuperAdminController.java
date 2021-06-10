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
@RequestMapping("/sadmin/v1/")
@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
public class SuperAdminController {

    @Autowired
    protected UserService userService;

    @PostMapping(value = "/createAdmin")
    public ApiResponse<UserDTO> save(@RequestBody UserDTO userDTO, Authentication authentication) {
        log.info("Role list - {}", authentication.getAuthorities());
        log.info(userDTO.toString());
        return new ApiResponse<>(userService.save(authentication, userDTO));
    }

    @PutMapping(value = "/updateAdmin")
    public ApiResponse<UserDTO> update(@RequestBody UserDTO userDTO, Authentication authentication) {
        log.info(userDTO.toString());
        return new ApiResponse<>(userService.update(authentication, userDTO));
    }

    @GetMapping(value = "/listAdmin/{userId}")
    public ApiResponse<UserDTO> list(@PathVariable String userId) {
        return new ApiResponse<>(userService.list(userId));
    }

    @GetMapping(value = "/listAdmin")
    public ApiResponse<List<UserDTO>> listAll(Authentication authentication) {
        return new ApiResponse<>(userService.listAll(authentication));
    }

    @DeleteMapping(value = "/deleteAdmin/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        userService.delete(id);
        return new ApiResponse<>("User Deleted");
    }
}