package com.vehicle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vehicle.app.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String id;
    private String name;
    private String username;
    private String password;
    private String emailId;
    private boolean active;
    private List<String> vehicleNumber;
    private List<String> imeiSimNum;

    public static User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmailId(userDTO.getEmailId());
        user.setPassword(userDTO.getPassword());
        user.setActive(userDTO.isActive());
        return user;
    }

    public static UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setActive(user.isActive());
        userDTO.setEmailId(user.getEmailId());
        return userDTO;
    }

    public static void convertToExisitingUser(User existingUser, UserDTO userDTO) {
        existingUser.setName(userDTO.getName());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmailId(userDTO.getEmailId());
        existingUser.setActive(userDTO.isActive());
    }
}