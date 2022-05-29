package com.shu.carsystem.service;

import com.shu.carsystem.common.Result;
import com.shu.carsystem.entity.Maintain;
import com.shu.carsystem.entity.User;

import com.shu.carsystem.pojo.ProjectRepairman;
import com.shu.carsystem.pojo.RepairmanRecord;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface UserService {
    Result[] inquire(User user);

    Result inquireByName(String id);

    Result[] showRepairman(Integer pageNo,Integer pageSize,String keyword);

    Result addNewMaintain(Map<String, Object> map);

    Result affirmMaintain(Map<String,Object> map);

    Result finishMaintain(Map<String,Object> map);

}
