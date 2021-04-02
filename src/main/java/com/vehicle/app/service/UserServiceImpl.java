package com.vehicle.app.service;

import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.UserDTO;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("customUserDetailsService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VehicleRepository vehicleRepository;

    public UserDTO save(UserDTO userDTO) {
        User user=UserDTO.convertToUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user=userRepository.save(user);
        return UserDTO.convertToDTO(user);
    }

    public UserDTO list(Long userId) {
        User user=userRepository.findById(userId).get();
        List<Vehicle> vehicles=vehicleRepository.findByUserId(userId);
        UserDTO userDTO= UserDTO.convertToDTO(user);
        List<String> vehicleNumber= vehicles.stream().map(vehicle -> vehicle.getNumber()).collect(Collectors.toList());
        userDTO.setVehicleNumber(vehicleNumber);
        return userDTO;
    }

    public List<UserDTO> listAll(){
        List<User> user=userRepository.findAll();
        return user.stream().map(UserDTO::convertToDTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("No User Found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("User name :"+username);
        User user=userRepository.findByUsername(username);
        log.info(user.toString()+"++++++++++++++++++");
        if(user==null){
            log.info("User null");
            throw new UsernameNotFoundException("User does not exists  "+username);
        }
        return user;
    }
}
