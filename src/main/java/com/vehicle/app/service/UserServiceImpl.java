package com.vehicle.app.service;

import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.UserDTO;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        User user=UserDTO.convertToUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user=userRepository.save(user);
        return UserDTO.convertToDTO(user);
    }

    public UserDTO list(Long userId) {
        User user=userRepository.findById(userId).get();
        List<Vehicle> vehicles=vehicleRepository.findByUserId(userId);
        List<Device> devices=deviceRepository.findByUserId(userId);
        UserDTO userDTO= UserDTO.convertToDTO(user);
        List<String> imeiSimNum=devices.stream().map(dev->{return "Imei : "+dev.getImei()+", Sim Number : "+dev.getSimNumber();}).collect(Collectors.toList());
        List<String> vehicleNumber= vehicles.stream().map(vehicle -> vehicle.getNumber()).collect(Collectors.toList());
        userDTO.setVehicleNumber(vehicleNumber);
        userDTO.setImeiSimNum(imeiSimNum);
        return userDTO;
    }

    public List<UserDTO> listAll(){
        List<User> user=userRepository.findAll();
        return user.stream().map(UserDTO::convertToDTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Optional.ofNullable(id).filter(i->i>0).orElseThrow(()->new RuntimeException("Please provide user id"));
        final User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User doesn't found"));
        userRepository.deleteById(id);
    }

    public UserDTO update(UserDTO userDTO) {
        Long userId = userDTO.getId();
        Optional.ofNullable(userId).filter(ui->ui>0).orElseThrow(()-> new RuntimeException("Please provide User Id"));
        User existingUser = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User does not exist"));
        UserDTO.convertToExisitingUser(existingUser,userDTO);
        existingUser = userRepository.save(existingUser);
        return UserDTO.convertToDTO(existingUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User does not exists  "+username);
        }
        return user;
    }
}
