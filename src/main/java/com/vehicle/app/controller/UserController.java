package com.vehicle.app.controller;

import com.vehicle.app.entity.User;
import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.model.UserDTO;
import com.vehicle.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save")
    public ApiResponse<UserDTO> save(@RequestBody UserDTO userDTO, Authentication authentication) {
        return new ApiResponse<>(userService.save(userDTO));
    }

    @PutMapping(value="/update")
    public UserDTO update(@RequestBody UserDTO userDTO){
        return userService.update(userDTO);
    }
    @GetMapping(value="/list/{userId}")
    public UserDTO list(@PathVariable Long userId){
        return userService.list(userId);
    }

    @GetMapping(value="/list")
    public List<UserDTO> listAll() {
        return userService.listAll();
    }

    @DeleteMapping(value="/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id){
        userService.delete(id);
        return new ApiResponse<>("User Deleted");
    }
}
