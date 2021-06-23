package com.vehicle.app.entity;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter

@Document("Role")
public class Role {

    private String id;
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