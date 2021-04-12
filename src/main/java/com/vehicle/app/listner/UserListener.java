package com.vehicle.app.listner;

import com.vehicle.app.entity.User;
import com.vehicle.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
@Slf4j
@Component
public class UserListener {

    @PrePersist
    public void logNewUserAttempt(User user) {
        log.info("###########################################################");
        log.info("Attempting to add new user with username: " + user.getUsername());
        user.setLevel(user.getParent().getLevel()+"/"+user.getUsername());
        log.info("###########################################################");
    }

    @PostPersist
    public void logNewUserAdded(User user) {
        log.info("###########################################################");
        log.info("Added user '" + user.getUsername() + "' with ID: " + user.getId());
        log.info("###########################################################");
    }
}
