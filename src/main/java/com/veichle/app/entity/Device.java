package com.veichle.app.entity;

import com.veichle.app.enums.DeviceType;
import com.veichle.app.enums.Operator;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Device extends BaseEntity {

    @Column(nullable=false,unique = true)
    private String imei;
    @Column(nullable=false,unique = true)
    private String simNumber;
    @Column(nullable=false)
    private Operator operator;
    @Column(nullable=false)
    private DeviceType deviceType;
    @Column(nullable=false)
    private boolean active;

}
