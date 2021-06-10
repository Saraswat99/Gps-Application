package com.vehicle.app.listner;

import com.vehicle.app.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserListener extends AbstractMongoEventListener<User> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        User user = event.getSource();
        if(user.getLevel()==null){
            log.info("###########################################################");
            log.info("Attempting to add new user with username: " + user.getUsername());
            user.setLevel(user.getParent().getLevel() + "/" + user.getUsername());
            super.onBeforeConvert(event);
            log.info("###########################################################");
        }
    }
}