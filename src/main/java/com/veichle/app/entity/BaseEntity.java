package com.veichle.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Long id;
    @CreatedBy
    protected String createdBy;
    @LastModifiedBy
    protected String modifiedBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added", columnDefinition = "DATETIME")
    protected Date added;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", columnDefinition = "DATETIME")
    protected Date updated;
}
