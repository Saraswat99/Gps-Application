package com.vehicle.app.ServiceImpl;

import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.Role;
import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.enums.ApiConstant;
import com.vehicle.app.enums.Roles;
import com.vehicle.app.model.UserDTO;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import com.vehicle.app.service.RoleService;
import com.vehicle.app.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("customUserDetailsService")
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeviceRepository deviceRepository;
    private final VehicleRepository vehicleRepository;

    @PostConstruct
    private void init() {
    }

    @Override
    public UserDTO save(Authentication authentication, UserDTO userDTO) {
        User parent = (User) authentication.getPrincipal();
        User user = UserDTO.convertToUser(userDTO);
        user.setParent(parent);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Roles roles = Roles.getChildRole(parent.getRoleAlisa());
        Role childRole = roleService.findByName(roles.name());
        user.setRoles(Set.of(childRole));
        user.setRoleAlisa(roles.getAlisa());
        user = userRepository.save(user);
        return UserDTO.convertToDTO(user);
    }

    @Override
    public UserDTO list(String userId) {
        User user = userRepository.findById(userId).get();
        List<Vehicle> vehicles = vehicleRepository.findByUserId(userId);
        List<Device> devices = deviceRepository.findByUserId(userId);
        UserDTO userDTO = UserDTO.convertToDTO(user);
        List<String> imeiSimNum = devices.stream().map(device -> device.getImei()).collect(Collectors.toList());
        List<String> vehicleNumber = vehicles.stream().map(vehicle -> vehicle.getNumber()).collect(Collectors.toList());
        userDTO.setVehicleNumber(vehicleNumber);
        userDTO.setImeiSimNum(imeiSimNum);
        return userDTO;
    }

    @Override
    public List<UserDTO> listAll(Authentication authentication) {
        List<User> user = userRepository.findByCreatedBy(authentication.getName());
        return user.stream().map(UserDTO::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO update(Authentication authentication, UserDTO userDTO) {
        String userId = userDTO.getId();
        Optional.ofNullable(userId).filter(ui -> !ui.isEmpty()).orElseThrow(() -> new RuntimeException(ApiConstant.PROVIDE_USER_ID));
        User existingUser = userRepository.findByIdAndCreatedBy(userId, authentication.getName()).orElseThrow(() -> new RuntimeException(ApiConstant.USER_DOES_NOT_EXIST));
        UserDTO.convertToExisitingUser(existingUser, userDTO);
        existingUser = userRepository.save(existingUser);
        return UserDTO.convertToDTO(existingUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void delete(String id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException(ApiConstant.NO_USER_FOUND));
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(ApiConstant.USER_DOES_NOT_EXIST + username);
        }
        return user;
    }
}