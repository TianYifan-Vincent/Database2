package com.shu.carsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Project {
    private Integer proId;
    private String pname;
    private float hour;
    private String pjob;
}
