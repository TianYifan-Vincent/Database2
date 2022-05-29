package com.shu.carsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotEnsuredRepair {
    private Integer repairId;
    private String license;
    private String vin;
    private Integer proId;
    private String pname;
    private String failure;
}