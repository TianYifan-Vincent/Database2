package com.shu.carsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintainInfo1 {
    private Integer proId;
    private String pname;
    private float hour;
    private String name;
    private Integer number;
    private float cost;
}
