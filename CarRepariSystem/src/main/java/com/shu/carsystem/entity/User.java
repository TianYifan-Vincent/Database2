package com.shu.carsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Integer userId;
    private String name;
    private String password;
    private String phone;
    private Integer isRepairman;
    private String job;
    private String realname;
    private String statu = "空闲中";

    public User(Integer userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}