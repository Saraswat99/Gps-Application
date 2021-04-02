package com.vehicle.app.entity;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@ToString
@Entity
@Slf4j
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String name;
    @Column(nullable=false, unique = true)
    private String username;
    @Column(nullable=false)
    private String password;
    @Column(nullable=false, unique = true)
    private String emailId;
    @Column
    private boolean active;
    @OneToMany(mappedBy="user", fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Vehicle> vehicles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        log.info("getUsername -----------------------");
        return username;
    }

    @Override
    public String getPassword(){
        log.info("getPassword -----------------------");
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        log.info("isAccountNonExpired -----------------------");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        log.info("isAccountNonLocked -----------------------");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        log.info("isCredentialsNonExpired -----------------------");
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}