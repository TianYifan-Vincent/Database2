package com.shu.carsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRepairman {
    private Integer proId;
    private String pname;
    private String hour;
    private Integer userId;
    private String name;
    private String phone;
    private String job;
    private String statu = "空闲中";
    private Integer repairId;
    private String realname;
}
