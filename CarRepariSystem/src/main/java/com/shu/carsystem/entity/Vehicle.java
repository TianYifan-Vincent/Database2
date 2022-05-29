package com.shu.carsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {
    private Integer vehicleId;
    private String license;
    private String color;
    private String type;
    private String category;        // 注意：和 sql 里 class 字段不对应
    private String vin;
    private Integer clientId;
}
