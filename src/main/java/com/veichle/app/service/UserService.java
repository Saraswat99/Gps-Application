package com.veichle.app.service;

import com.veichle.app.entity.User;
import com.veichle.app.entity.Vehicle;
import com.veichle.app.model.UserDTO;
import com.veichle.app.repository.UserRepository;
import com.veichle.app.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    //get all the user
    public UserDTO findUser(Long userId) {
        User user=userRepository.findById(userId).get();
        List<Vehicle> vehicles=vehicleRepository.findByUserId(userId);
        UserDTO userDTO = UserDTO.convertToUserDTO(user);
        List<String> vehicleNumber = vehicles.stream().map(vehicle -> vehicle.getNumber()).collect(Collectors.toList());
        userDTO.setVehicleNumber(vehicleNumber);
        return userDTO;
    }

    //save the user
    public UserDTO save(UserDTO userDTO) {
        User user = userDTO.convertToUser(userDTO);
        user = userRepository.save(user);
        return UserDTO.convertToUserDTO(user);
    }
}