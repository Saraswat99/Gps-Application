package com.vehicle.app.entity;

import com.vehicle.app.enums.DeviceType;
import com.vehicle.app.enums.SimOperator;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@ToString
@Setter
@Getter
@Document
public class Device extends BaseEntity  {

    @Indexed(unique = true)
    private String imei;
    @Indexed(unique = true)
    private String simNumber;
    @Field
    private boolean active;
    @Field
    private boolean assigned;
    @Field
    private String level;
    @Field
    private SimOperator simOperator;
    @Field
    private DeviceType deviceType;
    @DBRef
    private User user;

}