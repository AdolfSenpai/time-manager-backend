package com.ed.timemanager.auth_module.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ed.timemanager.commons.models.BaseModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "user")
@SuperBuilder(toBuilder = true)
public class User extends BaseModel {
    
    private final String email;

    private final String name;

    private final String password;
}
