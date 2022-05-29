package com.shu.carsystem.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shu.carsystem.common.Result;
import com.shu.carsystem.common.ResultEnum;
import com.shu.carsystem.entity.*;
import com.shu.carsystem.mapper.*;
import com.shu.carsystem.pojo.*;
import com.shu.carsystem.service.RepairService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private MaintainMapper maintainMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public List<Repair> getListByUId(Integer id) {
        return repairMapper.getListByUId(id);
    }

    @Override
    public Result showRepairToReceive(Integer pageNo,Integer pageSize,String keyword) {
        PageHelper.startPage(pageNo,pageSize);
        List<Repair> repairs = repairMapper.showRepairToReceive(keyword);
        PageInfo<Repair> repairPageInfo = new PageInfo<>(repairs);
        return Result.create(ResultEnum.QUERY_SUCCESS,repairPageInfo);
    }


    @Override
    public Result showRepairInProgress(Integer pageNo,Integer pageSize,String keyword) {
        PageHelper.startPage(pageNo,pageSize);
        List<Repair> repairs = repairMapper.showRepairInProgress(keyword);
        PageInfo<Repair> repairPageInfo = new PageInfo<>(repairs);
        return Result.create(ResultEnum.QUERY_SUCCESS,repairPageInfo);
    }

    @Override
    public Result showRepairComplete(Integer pageNo, Integer pageSize,String keyword) {
        PageHelper.startPage(pageNo,pageSize);
        List<Repair> repairs = repairMapper.showRepairComplete(keyword);
        PageInfo<Repair> repairPageInfo = new PageInfo<>(repairs);
        return Result.create(ResultEnum.QUERY_SUCCESS,repairPageInfo);
    }

    @Override
    public Result showAllRepair(Integer pageNo, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNo,pageSize);
        List<RepairVehicle> repairs = repairMapper.showAllRepair(keyword);
        PageInfo<RepairVehicle> repairPageInfo = new PageInfo<>(repairs);
        return Result.create(ResultEnum.QUERY_SUCCESS,repairPageInfo);
    }

    @Override
    public Result updateRepair(Repair repair,Integer userId) {
        repair.setStatu("等待派单"); //修改状态
        repair.setUserId(userId);   //设置业务员编号

        /**
         * 更新之前 要先判断，userId vechiledId maintainId 是否存在，若不存在则说明，非法，不予更新，并且返回错位信息
         */
        String ddl = repair.getDdl();
        if(ddl != null){
            Integer month = Integer.parseInt(ddl.substring(5,7));
            Integer year = Integer.parseInt(ddl.substring(0,4));
            Integer day = Integer.parseInt(ddl.substring(8,10));
            Integer hour = Integer.parseInt(ddl.substring(11,13));
            Integer minute = Integer.parseInt(ddl.substring(14,16));
            Integer second = Integer.parseInt(ddl.substring(17,19));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, hour, minute, second);
            calendar.add(Calendar.HOUR,8);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ddl1 = format.format(calendar.getTime());
            repair.setDdl(ddl1);
        }
        User user = userMapper.inqurieUserByUserId(userId);
        if(user == null) return Result.create(ResultEnum.USER_NOT_EXIST,userId);
        Vehicle vehicle = vehicleMapper.getVehicleByVehicleId(repair.getVehicleId());
        if(vehicle == null) return Result.create(ResultEnum.Vehicle_NOT_EXISTS,repair.getVehicleId());

        int i = repairMapper.updateRepair(repair);//更新
        if(i==0) return Result.create(ResultEnum.UPDATE_ERROR,repair);
        else return Result.create(ResultEnum.UPDATE_SUCCESS,repair);
    }

    @Override
    public Result showNotEnsuredRepairs(Integer userId, Integer pageNo, Integer pageSize, String keyWord) {
        //获取维修员首页数据，获取维修员需要确认的订单数据
        PageHelper.startPage(pageNo, pageSize);
        List<NotEnsuredRepair> repairs = maintainMapper.getNotEnsuredRepairs(userId, keyWord);
        PageInfo<NotEnsuredRepair> repairPageInfo = new PageInfo<NotEnsuredRepair>(repairs);
        if (repairs == null) return Result.create(ResultEnum.UNKNOWN_ERROR,null);
        return Result.create(ResultEnum.SUCCESS, repairPageInfo);
    }

    @Override
    public Result[] showOngoingRepairs(Integer userId, Integer pageNo, Integer pageSize) {
        // 不乐意写这一段的注释，是折磨
        Result[] results = new Result[2];
        List<MaintainInfo> ongoingMaintainList = new ArrayList<>();
        RepairmanRecord repairInfo = maintainMapper.getRepairInfoByRepairmanId(userId);
        results[0] = Result.create(ResultEnum.REPAIR_DATA,repairInfo);
        if (repairInfo == null){
            results[1] = Result.create(ResultEnum.REPAIR_DATA,ongoingMaintainList);
            return results;
        }
        Integer repairId = repairInfo.getRepairId();
        Integer repairmanId = userId;
        PageHelper.startPage(pageNo, pageSize);
        List<Project> ongoingProjectList = maintainMapper.getOngoingProjectList(repairId, repairmanId);
        if(ongoingProjectList == null)
        {
            results[1] = Result.create(ResultEnum.REPAIR_DATA,ongoingMaintainList);
            return results;
        }
        for(Project project : ongoingProjectList){
            MaintainInfo maintainInfo = new MaintainInfo();
            maintainInfo.setProId(project.getProId());
            maintainInfo.setPname(project.getPname());
            maintainInfo.setHour(project.getHour());
            List<MaterialDetail> list = maintainMapper.getMaterialDetail(repairId,repairmanId,project.getProId());
            System.out.println(list);
            List<Integer> matIds = new ArrayList<>();
            List<String> names = new ArrayList<>();
            List<Integer> numbers = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                matIds.add(list.get(i).getMatId());
                names.add(list.get(i).getName());
                numbers.add(list.get(i).getNumber());
            }
            maintainInfo.setNumbers(numbers);
            maintainInfo.setNames(names);
            maintainInfo.setMatIds(matIds);
            ongoingMaintainList.add(maintainInfo);
        }
        PageInfo<MaintainInfo> pageInfo = new PageInfo<>(ongoingMaintainList);
        results[1] = Result.create(ResultEnum.REPAIR_DATA,pageInfo);
        return results;
    }

    @Override
    public Result queryMaterialName(Integer matId) {
        // 根据matId查询matName数组
        List<String> list = materialMapper.queryMaterialNameList(matId);
        return Result.create(ResultEnum.REPAIR_DATA, list);
    }

    @Override
    public Result queryMaterialName() {
        Integer matId = null;
        List<String> list = materialMapper.queryMaterialNameList(matId);
        return Result.create(ResultEnum.REPAIR_DATA, list);
    }


    @Override
    public Result queryFinishedMaintain(Integer userId, Integer pageNo, Integer pageSize, String keyWord) {
        //获取获取维修员已完成的订单数据
        PageHelper.startPage(pageNo, pageSize);
        List<NotEnsuredRepair> repairs = maintainMapper.getFinishedRepairs(userId, keyWord);
        PageInfo<NotEnsuredRepair> repairPageInfo = new PageInfo<NotEnsuredRepair>(repairs);
        if (repairs == null) return Result.create(ResultEnum.UNKNOWN_ERROR,null);
        return Result.create(ResultEnum.SUCCESS, repairPageInfo);
    }

    @Override
    public Result[] getAttorney(String vin,String failure) {
        Result[] results = new Result[2];
        RepairInfo repairInfoByVin = repairMapper.getRepairInfoByVin(vin, failure);
        results[0] = Result.create(ResultEnum.REPAIR_DATA,repairInfoByVin);
        Integer repairId = repairInfoByVin.getRepairId();
        List<MaintainInfo1> list = maintainMapper.getMaintainInfo1ByRid(repairId);
        System.out.println(list);
        results[1] = Result.create(ResultEnum.REPAIR_DATA,list);

        return results;
    }
}
