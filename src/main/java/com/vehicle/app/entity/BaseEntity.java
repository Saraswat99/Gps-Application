package com.vehicle.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import java.util.Date;

@Setter
@Getter
public class BaseEntity {

    @Id
    protected String id;
    @CreatedBy
    protected String createdBy;
    @LastModifiedBy
    protected String modifiedBy;
    @CreatedDate
    protected Date added;
    @LastModifiedDate
    protected Date updated;
}