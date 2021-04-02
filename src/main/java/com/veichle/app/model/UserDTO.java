package com.veichle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.veichle.app.entity.User;
import com.veichle.app.entity.Vehicle;
import com.veichle.app.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private long id;
    private String name;
    private String userName;
    private String password;
    private String email;
    private boolean active;
    private String added;
    private String updated;
    private  List<String> vehicleNumber;

    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setActive(user.isActive());
        userDTO.setAdded(DateTimeUtils.convertToString(user.getAdded()));
        userDTO.setUpdated(DateTimeUtils.convertToString(user.getUpdated()));
        return userDTO;
    }



    public static User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setActive(userDTO.isActive());
        return user;
    }
}

























/*
    public static void convertToExistingVehicle(Vehicle existingVehicle, UserDTO vehicleDTO) {
        existingVehicle.setLatitude(vehicleDTO.getLatitude());
        existingVehicle.setLongitude(vehicleDTO.getLongitude());
        existingVehicle.setVehicleType(VehicleType.valueOf(vehicleDTO.getVehicleType()));
        existingVehicle.setVehicleStatus(VehicleStatus.valueOf(vehicleDTO.getVehicleStatus()));
        existingVehicle.setActive(vehicleDTO.isActive());
}*/

