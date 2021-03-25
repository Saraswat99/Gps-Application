package com.veichle.app.entity;

import com.veichle.app.enums.DeviceType;
import com.veichle.app.enums.SimOperator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Setter
@Getter
@Entity
public class Device extends BaseEntity {

    @Column(nullable=false,unique = true)
    private String imei;
    @Column(nullable=false,unique = true)
    private String simNumber;
    @Column(nullable=false)
    private boolean active;
    @Column(nullable = false)
    private boolean assigned;
    @Column(nullable=false)
    private SimOperator simOperator;
    @Column(nullable=false)
    private DeviceType deviceType;
}