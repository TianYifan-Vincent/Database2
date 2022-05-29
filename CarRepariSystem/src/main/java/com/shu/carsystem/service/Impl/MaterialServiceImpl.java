package com.shu.carsystem.service.Impl;

import com.shu.carsystem.common.Result;
import com.shu.carsystem.common.ResultEnum;
import com.shu.carsystem.entity.Material;
import com.shu.carsystem.mapper.MaintainMapper;
import com.shu.carsystem.mapper.MaterialMapper;
import com.shu.carsystem.service.MaterialService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaintainMapper maintainMapper;
    @Override
    public Result insert(Material material) {
        int insert = materialMapper.insert(material);
        if(insert == 0) return Result.create(ResultEnum.INSERT_ERROR,material);
        else return Result.create(ResultEnum.INSERT_SUCCESS,material);
    }

    @Override
    public Result addMaterial(Map<String, Object> map) {
        Integer repairId = (Integer) map.get("repairId");
        Integer repairmanId = (Integer) map.get("repairmanId");
        LinkedHashMap<String,Object> map2 =(LinkedHashMap<String, Object>) map.get("maintain");
        List<String> names = (List<String>) map2.get("names");
        List<Integer> numbers = (List<Integer>) map2.get("numbers");
        List<Integer> matIds = (List<Integer>) map2.get("matIds");
        Integer proId = (Integer) map2.get("proId");
        String name = (String) map.get("name");
        Integer number = (Integer) map.get("number");
        Integer matId;
        if (number == null || name == null) return Result.create(ResultEnum.DATA_IS_NULL,null);
        if (names.contains(name)){
            int index = names.indexOf(name);
            matId = matIds.get(index);
            int ans = maintainMapper.setMaterialNumAddNumber(repairId, repairmanId, proId, number, matId);
            if(ans == 0) return Result.create(ResultEnum.INSERT_ERROR,null);
            return Result.create(ResultEnum.INSERT_SUCCESS,null);
        }
        else{
            matId = materialMapper.getMatIdByName(name);
            int ans = maintainMapper.addNewDetail(repairId, repairmanId, proId, number, matId);
            if(ans == 0) return Result.create(ResultEnum.INSERT_ERROR,null);
            return Result.create(ResultEnum.INSERT_SUCCESS,null);
        }
        }
}
