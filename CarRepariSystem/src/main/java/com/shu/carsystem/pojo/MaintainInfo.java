package com.shu.carsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintainInfo {
    private Integer proId;
    private String pname;
    private float hour = 0;
    private List<Integer> matIds;
    private List<String> names;
    private List<Integer> numbers;
}
