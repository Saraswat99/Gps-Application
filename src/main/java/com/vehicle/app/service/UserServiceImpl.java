package com.vehicle.app.service;

import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.model.UserDTO;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("customUserDetailsService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VehicleRepository vehicleRepository;
    private final DeviceRepository deviceRepository;

    public UserDTO save(UserDTO userDTO) {
        User user = UserDTO.convertToUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return UserDTO.convertToDTO(user);
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Please provide User Id"));
        List<Vehicle> vehicles = vehicleRepository.findByUserId(userId);
        List<Device> devices = deviceRepository.findByUserId(userId);
        UserDTO userDTO = UserDTO.convertToDTO(user);
        List<String> vehicleNumber = vehicles.stream().map(vehicle -> vehicle.getNumber()).collect(Collectors.toList());
        List<String> deviceImei = devices.stream().map(device -> device.getImei()).collect(Collectors.toList());
        userDTO.setVehicleNumber(vehicleNumber);
        userDTO.setDeviceImei(deviceImei);
        return userDTO;
    }

    public List<UserDTO> listAll(){
        List<User> user = userRepository.findAll();
        return user.stream().map(UserDTO::convertToDTO).collect(Collectors.toList());
    }

    public  UserDTO update(UserDTO userDTO, Authentication authentication){
        Long userId = userDTO.getId();
        Optional.ofNullable(userId).filter(ui->ui>0).orElseThrow(()-> new RuntimeException("Please provide User Id"));
        User existingUser = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User does not exist"));
        log.info("Id {}",existingUser.getId());
        UserDTO.convertToExistingUser(existingUser,userDTO);
        existingUser = userRepository.save(existingUser);
        return UserDTO.convertToDTO(existingUser);
    }

    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(()-> new RuntimeException("No User Found"));
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("User name :"+username);
        User user = userRepository.findByUsername(username);
        Optional.ofNullable(user).filter(vi->vi!=null).orElseThrow(()-> new UsernameNotFoundException("User does not exists  "+username));
        return user;
    }
}