package com.shu.carsystem.service.Impl;

import com.shu.carsystem.entity.Vehicle;
import com.shu.carsystem.mapper.VehicleMapper;
import com.shu.carsystem.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public List<Vehicle> getListByCId(Integer id) {
        return vehicleMapper.getListByCId(id);
    }
}
