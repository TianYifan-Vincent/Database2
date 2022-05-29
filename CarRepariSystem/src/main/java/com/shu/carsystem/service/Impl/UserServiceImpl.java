package com.shu.carsystem.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shu.carsystem.common.Result;
import com.shu.carsystem.common.ResultEnum;
import com.shu.carsystem.entity.Client;
import com.shu.carsystem.entity.Maintain;
import com.shu.carsystem.entity.User;
import com.shu.carsystem.mapper.ClientMapper;
import com.shu.carsystem.mapper.MaintainMapper;
import com.shu.carsystem.mapper.RepairMapper;
import com.shu.carsystem.mapper.UserMapper;
import com.shu.carsystem.pojo.ProjectRepairman;
import com.shu.carsystem.pojo.RepairmanRecord;
import com.shu.carsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private MaintainMapper maintainMapper;


    //查询任意一位用户数据信息（思路：分别在Client和User表中查询）
    @Override
    public Result[] inquire(User user) {
        User user1 = userMapper.inquireUser(user);
        Result[] ret = new Result[2];
        if(user1 == null){

            Client client = new Client();
            client.setClientId2(user.getName());
            client.setPassword(user.getPassword());
            System.out.println(client);
            Client client1 = clientMapper.inquireClient(client);
            System.out.println(client1);
            if(client1 == null) {
                ret[0] = Result.create(ResultEnum.USER_NOT_EXIST,null);
                return ret;
            }

            ret[0] = Result.create(ResultEnum.QUERY_SUCCESS, client1);
            ret[1] = Result.create(ResultEnum.QUERY_SUCCESS, "Client");
            return ret;
        }

        ret[0] = Result.create(ResultEnum.QUERY_SUCCESS, user1);
        ret[1] = Result.create(ResultEnum.QUERY_SUCCESS, "User");
        return ret;
    }

    @Override
    public Result inquireByName(String id) {
        // 不传数据只传结果
        User user = userMapper.inquireUserByName(id);
        if(user == null){
            Client client = clientMapper.inquireClientByName(id);
            if(client == null) return Result.create(ResultEnum.USER_NOT_EXIST, null);
            return Result.create(ResultEnum.USER_IS_EXISTS, null);
        }
        return Result.create(ResultEnum.USER_IS_EXISTS,null);
    }

    @Override
    public Result[] showRepairman(Integer pageNo,Integer pageSize,String keyword) {
        // 查询所有的维修员，并且返回所有的订单号
        Result[] res = new Result[2];
        PageHelper.startPage(pageNo,pageSize);
        List<ProjectRepairman> projectRepairmen = userMapper.showRepairman(keyword);
        PageInfo<ProjectRepairman> pageInfo = new PageInfo<>(projectRepairmen);
        List<Integer> repairIdList = repairMapper.getDispatchedList();
        if(projectRepairmen == null) res[0] = Result.create(ResultEnum.UNKNOWN_ERROR, null);
        res[0] = Result.create(ResultEnum.QUERY_SUCCESS, pageInfo);
        if(repairIdList == null) res[1] = Result.create(ResultEnum.UNKNOWN_ERROR, null);
        res[1] = Result.create(ResultEnum.QUERY_SUCCESS, repairIdList);
        return res;
    }

    @Transactional
    @Override
    public Result addNewMaintain(Map<String, Object> map) {
        //将新获取到的maintain记录插入maintain表中，获取的参数为一个数组。其中存放的是维修人员id、委托员id、委托单id以及维修项目id
        // 需要注意的是将新纪录插入后，需要修改：维修员的状态、将委托的状态移至等待维修
        List<Object> list =(List<Object>) map.get("list");
        if(list.size() == 0 || list == null) return Result.create(ResultEnum.NOT_NULLABLE,null);
        int i = 0;
        Integer repairId = (Integer) map.get("repairId");
        Integer userId = (Integer) map.get("userId");
        for(Object object : list){
            try {
                LinkedHashMap<String,Object> map1 = (LinkedHashMap<String,Object>) object;
                Integer repairmanId = (Integer) map1.get("userId");
                Integer proId = (Integer) map1.get("proId");
                Maintain maintain = new Maintain(repairId,repairmanId, proId, "待确认", userId);
                maintainMapper.insertMaintain(maintain);
            }catch (Exception e){
                System.out.println("我异常了");
                System.out.println(e.toString());
                return Result.create(ResultEnum.ARRANGE_FAILURE,null);
            }
        }
        return Result.create(ResultEnum.ARRANGE_SUCCESS, null);
    }

    @Override
    public Result affirmMaintain(Map<String,Object> map) {
        RepairmanRecord repairmanRecord = new RepairmanRecord();
        repairmanRecord.setRepairmanId((Integer) map.get("repairmanId"));
        repairmanRecord.setFailure((String) map.get("failure"));
        repairmanRecord.setLicense((String) map.get("license"));
        repairmanRecord.setPname((String) map.get("pname"));
        repairmanRecord.setVin((String) map.get("vin"));
        repairmanRecord.setRepairId((Integer) map.get("repairId"));
        repairmanRecord.setProId((Integer) map.get("proId"));
        System.out.println(repairmanRecord);
        int i = maintainMapper.affirmMaintain(repairmanRecord);
        if(i == 0) return Result.create(ResultEnum.UPDATE_ERROR,null);
        return Result.create(ResultEnum.UPDATE_SUCCESS,null);
    }

    @Override
    public Result finishMaintain(Map<String, Object> map) {
        Integer repairId = (Integer) map.get("repairId");
        Integer repairmanId = (Integer) map.get("repairmanId");
        Integer proId = (Integer) map.get("proId");
        int i = maintainMapper.finishMaintain(repairId,repairmanId,proId);
        System.out.println(i);
        if(i == 0) return Result.create(ResultEnum.UNKNOWN_ERROR, null);
        return Result.create(ResultEnum.UPDATE_SUCCESS,null);
    }
}
