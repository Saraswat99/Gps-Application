package com.vehicle.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "role")
public class Role {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private boolean isDefault;
    @DBRef
    private List<User> users;
    public Role() {
    }

    public Role(String name, boolean isDefault) {
        this.name = name;
        this.isDefault = isDefault;
    }
}