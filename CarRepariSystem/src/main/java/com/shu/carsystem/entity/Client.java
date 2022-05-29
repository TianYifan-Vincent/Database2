package com.shu.carsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client {
    private Integer clientId;
    private String name;
    private String nature;
    private Double discount;
    private String contact;
    private String phone;
    private String clientId2;
    private String password;
}
