package com.vehicle.app.entity;

import com.vehicle.app.enums.DeviceType;
import com.vehicle.app.enums.SimOperator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Device extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String imei;
    @Column(nullable = false, unique = true)
    private String simNumber;
    @Column(nullable = false, name = "active", columnDefinition = "boolean default false")
    private boolean active;
    @Column(nullable = false, name = "assigned", columnDefinition = "boolean default false")
    private boolean assigned;
    @Column(nullable = false)
    private SimOperator simOperator;
    @Column(nullable = false)
    private DeviceType deviceType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}