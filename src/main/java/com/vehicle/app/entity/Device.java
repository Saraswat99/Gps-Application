package com.vehicle.app.entity;

import com.vehicle.app.enums.DeviceType;
import com.vehicle.app.enums.SimOperator;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.vehicle.app.entity.BaseEntity;

@Data
@Document
public class Device extends BaseEntity {

    @Indexed(unique = true)
    private String imei;
    @Indexed(unique = true)
    private String simNumber;
    private boolean active;
    private boolean assigned;
    private SimOperator simOperator;
    private DeviceType deviceType;
    @DBRef
    private User user;
    private String level;
}