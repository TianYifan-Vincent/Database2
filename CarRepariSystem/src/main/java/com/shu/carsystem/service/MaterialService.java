package com.shu.carsystem.service;

import com.shu.carsystem.common.Result;
import com.shu.carsystem.entity.Material;

import java.util.Map;

public interface MaterialService {
    Result insert(Material material);

    Result addMaterial(Map<String, Object> map);
}
