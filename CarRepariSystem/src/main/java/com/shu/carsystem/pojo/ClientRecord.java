package com.shu.carsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRecord {
    private Integer repairId;
    private String vin;
    private String license;
    private String category;
    private String failure;
    private String ddl;
    private String statu;
    private String approachTime;
}
