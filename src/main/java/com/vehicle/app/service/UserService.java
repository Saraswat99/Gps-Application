package com.vehicle.app.service;

import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.model.UserDTO;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
public interface UserService extends UserDetailsService {

    UserDTO save(UserDTO userDTO);

    UserDTO list(Long userId);

    List<UserDTO> listAll();

    void delete(Long id);
}
