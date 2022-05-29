package com.shu.carsystem.mapper;

import com.shu.carsystem.entity.Vehicle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VehicleMapper {
    //根据Client_id获取车辆信息
    List<Vehicle> getListByCId(Integer id);

    int containLicenseOrVin(@Param("License") String License,@Param("Vin") String Vin);

    int insertVehicle(Vehicle vehicle);

    Object getVidByVin(String vin);

    Vehicle getVehicleByVehicleId(@Param("vehicleId") Integer vehicleId);

}
