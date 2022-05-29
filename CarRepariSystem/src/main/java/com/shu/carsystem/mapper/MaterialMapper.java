package com.shu.carsystem.mapper;

import com.shu.carsystem.entity.Material;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface MaterialMapper {
    int insert(Material material);

    List<String> queryMaterialNameList(Integer matId);

    Integer getMatIdByName(String name);
}
