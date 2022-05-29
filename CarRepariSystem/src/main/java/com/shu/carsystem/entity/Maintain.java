package com.shu.carsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Maintain {
    private Integer repairId;
    private Integer repairmanId;
    private Integer proId;
    private String isFinished = "待确认";
    private Integer userId;
}
