package com.veichle.app.controller;

import com.veichle.app.entity.User;
import com.veichle.app.model.UserDTO;
import com.veichle.app.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/list/{userId}")
    public UserDTO getUserByName(@PathVariable("userId") Long userId){
        log.info("UserId ==>> {}",userId);
        return userService.findUser(userId);
    }

    @RequestMapping(method= RequestMethod.POST,value="/save")
    public UserDTO save(@RequestBody UserDTO userDTO) {
        log.info(userDTO.toString());
        return userService.save(userDTO);
    }
}