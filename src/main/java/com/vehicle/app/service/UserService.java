package com.vehicle.app.service;

import com.vehicle.app.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserDTO save(UserDTO userDTO);

    UserDTO getUserById(Long userId);

    List<UserDTO> listAll();

    void delete(Long id);

    UserDTO update(UserDTO userDTO, Authentication authentication);
}
