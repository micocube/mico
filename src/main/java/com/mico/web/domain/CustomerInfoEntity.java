package com.mico.web.domain;

import com.mico.web.core.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="customerInfo")
public class CustomerInfoEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -7920396843754746995L;
    private String name;
    private String gender;
    private String email;
    private String mobile_num;
    
    public CustomerInfoEntity(String name, String gender, String email,
            String mobile_num) {
        super();
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.mobile_num = mobile_num;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile_num() {
        return mobile_num;
    }
    public void setMobile_num(String mobile_num) {
        this.mobile_num = mobile_num;
    }
}