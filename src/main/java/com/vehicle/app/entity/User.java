package com.vehicle.app.entity;

import com.vehicle.app.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Slf4j
@Entity
@Table
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String name;
    @Column(nullable=false, unique = true)
    private String username;
    @Column
    private String password;
    @Column(nullable=false, unique = true)
    private String emailId;
    @Column
    private boolean active;
    @OneToMany(mappedBy="user", fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Vehicle> vehicles;
    @OneToMany(mappedBy="user", fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Device> devices;
    @ManyToMany(mappedBy="users", fetch = FetchType.LAZY)
    private List<Role> roles;

    private User user;

    @Autowired
    private RoleRepository roleRepository;

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) { this.password = password; }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        List<Role> roles = user.getRoles();
        log.info("roles",roles);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleUser()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public List<Role> getRoles() {
        return  roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
/*
    @PostConstruct
    public void saveUser(){
        List<RoleUser>  roleUsers = Arrays.asList(RoleUser.valueOf("SUPER_ADMIN"),RoleUser.valueOf("ADMIN"));
        roleUsers = roleRepository.saveUsers(roleUsers);
    }*/
}