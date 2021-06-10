package com.vehicle.app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;

import java.util.Date;

@Setter
@Getter
@ToString
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