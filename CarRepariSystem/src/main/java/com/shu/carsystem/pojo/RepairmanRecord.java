package com.shu.carsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairmanRecord {
    @Nullable
    private Integer repairId;
    @Nullable
    private String vin;
    @Nullable
    private String license;
    @Nullable
    private Integer proId;
    @Nullable
    private String pname;
    @Nullable
    private String failure;
    @Nullable
    private Integer repairmanId;

    public boolean isEmpty(){
        if (repairmanId == null && repairId ==null) return true;
        return false;
    }
}
