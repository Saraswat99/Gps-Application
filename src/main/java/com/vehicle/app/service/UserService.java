package com.vehicle.app.service;

import com.vehicle.app.entity.User;
import com.vehicle.app.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void delete(String id);

    UserDTO list(String userId);

    List<UserDTO> listAll(Authentication authentication);

    UserDTO save(Authentication authentication, UserDTO userDTO);

    UserDTO update(Authentication authentication, UserDTO userDTO);

    User findByUsername(String username);
}
