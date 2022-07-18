package com.tianci.model.user.pojo;

import lombok.Data;

import java.util.Date;

@Data   // generate Getter, Setter method
public class TcUserObject {
    private Long id;
    private String salt;
    private String name;
    private String password;
    private String phone;
    private String image;
    private Boolean sex;
    private Boolean isCertification;
    private Boolean isIdentityAuthentication;
    private Boolean status;
    private int flag;
    private Date loginTime;
    private Date createdTime;
}
