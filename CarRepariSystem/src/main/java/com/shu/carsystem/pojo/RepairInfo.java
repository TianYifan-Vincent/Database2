package com.shu.carsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairInfo {
    public Integer repairId;
    private String license;
    private Integer clientId;
    private String repairType;
    private String repairClassification;
    private String payment;
    private String vin;
    private Double mile;
    private Double fuel;
    private String approachTime;
    private String realname;
    private Integer userId;
    private String ddl;
    private String failure;
    private float discount;
    private float hour;
    private float cost;
}
