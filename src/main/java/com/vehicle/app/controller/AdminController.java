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
    public ApiResponse<UserDTO> save(@RequestBody UserDTO userDTO, Authentication authentication) {
        log.info("Role list - {}", authentication.getAuthorities());
        log.info(userDTO.toString());
        return new ApiResponse<>(userService.save(authentication, userDTO));
    }

    @PutMapping(value = "/updateClient")
    public UserDTO update(@RequestBody UserDTO userDTO, Authentication authentication) {
        log.info(userDTO.toString());
        return userService.update(authentication, userDTO);
    }

    @GetMapping(value = "/listClient/{userId}")
    public UserDTO list(@PathVariable Long userId) {
        return userService.list(userId);
    }

    @GetMapping(value = "/listClient")
    public List<UserDTO> listAll(Authentication authentication) {
        return userService.listAll(authentication);
    }

    @DeleteMapping(value = "/deleteClient/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ApiResponse<>("User Deleted");
    }
}
