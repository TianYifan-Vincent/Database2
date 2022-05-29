package com.shu.carsystem.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import com.shu.carsystem.common.Result;
import com.shu.carsystem.common.ResultEnum;
import com.shu.carsystem.entity.Client;
import com.shu.carsystem.entity.Repair;
import com.shu.carsystem.entity.Vehicle;
import com.shu.carsystem.mapper.ClientMapper;
import com.shu.carsystem.mapper.RepairMapper;
import com.shu.carsystem.mapper.VehicleMapper;
import com.shu.carsystem.pojo.ClientRecord;
import com.shu.carsystem.pojo.ProjectRepairman;
import com.shu.carsystem.service.ClientService;
import com.shu.carsystem.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Override
    public Client inquireClient(Client client) {
        return clientMapper.inquireClient(client);
    }

    @Override
    public int insertClient(Client client) {
        return clientMapper.insertClient(client);
    }

    @Override
    public int updatePasswordById(Integer id, String password) {
        return clientMapper.updatePasswordById(id, password);
    }

    @Override
    public int updateInfoById(Integer id, String name, String nature, String contact, String phone) {
        return clientMapper.updateInfoById(id, name, nature, contact, phone);
    }

    @Override
    public Result addClientInfo(Map<String, Object> map) {
        //Client页面的Post请求分为两类：新增车辆和新增委托
        String start= "(" +

                "京|津|冀|晋|蒙|辽|吉|黑|沪|苏|浙|皖|闽|赣|鲁|" +

                "豫|鄂|湘|粤|桂|琼|渝|川|贵|云|藏|陕|甘|青|宁|" +

                "新|港|澳|" +

                "军|空|海|北|沈|兰|济|南|广|成|临" +

                ")";
        String regex = start+"[a-zA-Z]{1}[a-zA-Z0-9]{5}";
        int cnt = 0;
        if(map.containsKey("license")){
            //请求参数包含车牌号，说明此为新增车辆请求。
            //车架号存在/牌照号存在/牌照号不符合规格
            if(vehicleMapper.containLicenseOrVin((String) map.get("license"),(String) map.get("vin")) != 0){
                return Result.create(ResultEnum.USER_IS_EXISTS,null);
            }
            if(!((String) map.get("license")).matches(regex))
                return Result.create(ResultEnum.BAD_FORMAT,(String) map.get("license"));
            //完全符合规则，需要将该车辆插入至Vehicle表中。
            Vehicle vehicle = new Vehicle();
            vehicle.setClientId((Integer) map.get("clientId"));
            vehicle.setCategory((String) map.get("class"));
            vehicle.setLicense((String) map.get("license"));
            vehicle.setColor((String) map.get("color"));
            vehicle.setType((String) map.get("type"));
            vehicle.setVin((String) map.get("vin"));
            cnt = vehicleMapper.insertVehicle(vehicle);
            if(cnt == 0) return Result.create(ResultEnum.UNKNOWN_ERROR,null);
            return Result.create(ResultEnum.INSERT_SUCCESS,vehicle);
        }
        else{
//            String[] mon = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
//            List<String> months = Arrays.asList(mon);
            //请求参数包含粗略故障，说明是新增客户委托
            //判断大致分为：是否存在对应车架号？该车架号是否已登陆过维修？
            String vin =(String) map.get("vin");
            Integer vehicleId = (vehicleMapper.getVidByVin(vin) == null) ? -1 : (Integer) vehicleMapper.getVidByVin(vin);
            if(vehicleId == -1) return Result.create(ResultEnum.USER_NOT_EXIST,null);
            if(vehicleMapper.containLicenseOrVin(null, vin) == 0) return Result.create(ResultEnum.USER_NOT_EXIST,null);
            Repair repair = new Repair();
            repair.setPayment((String) map.get("payment"));
            repair.setVehicleId(vehicleId);
            repair.setFailure((String) map.get("failure"));
            String temp = map.get("fuel").toString();
            Double fuel = Double.valueOf(temp);
            fuel /= 10;
            repair.setFuel(Double.parseDouble(String.format("%.2f",fuel).toString()));
            repair.setMile(Double.parseDouble(map.get("mile").toString()));
            String time = map.get("approach_time").toString();
            Integer month = Integer.parseInt(time.substring(5,7));
            Integer year = Integer.parseInt(time.substring(0,4));
            Integer day = Integer.parseInt(time.substring(8,10));
            Integer hour = Integer.parseInt(time.substring(11,13));
            Integer minute = Integer.parseInt(time.substring(14,16));
            Integer second = Integer.parseInt(time.substring(17,19));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, hour, minute, second);
            calendar.add(Calendar.HOUR,8);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String approachTime = format.format(calendar.getTime());
            repair.setApproachTime(approachTime);

            cnt = repairMapper.insertClientRepair(repair);
            if(cnt == 0) return Result.create(ResultEnum.UNKNOWN_ERROR,null);
            return Result.create(ResultEnum.INSERT_SUCCESS,repair);
        }
    }

    @Override
    public Result queryOngoingHistory(Integer clientId, Integer pageNo, Integer pageSize, String keyWord) {
        //查询客户进行中的历史委托
        //流程大概为到Repair表中查询本clientId下所有状态为除已完成的维修委托
        PageHelper.startPage(pageNo, pageSize);
        List<ClientRecord> list = repairMapper.queryClientHistoryRecord(clientId, keyWord);
        PageInfo<ClientRecord> pageInfo = new PageInfo<>(list, 5);
        if(list == null) return Result.create(ResultEnum.UNKNOWN_ERROR,null);
        return Result.create(ResultEnum.SUCCESS, pageInfo);
    }

    @Override
    public Result queryFinishedHistory(Integer clientId, Integer pageNo, Integer pageSize, String keyWord) {
        //查询客户已完成中的历史委托
        //流程大概为到Repair表中查询本clientId下所有状态为已完成的维修委托
        PageHelper.startPage(pageNo, pageSize);
        List<ClientRecord> list = repairMapper.queryClientFinishedRecord(clientId, keyWord);
        PageInfo<ClientRecord> pageInfo = new PageInfo<>(list, 5);
        if(list == null) return Result.create(ResultEnum.UNKNOWN_ERROR,null);
        return Result.create(ResultEnum.SUCCESS, pageInfo);
    }

    @Override
    public Result updateClient(Client client) {
        int i = clientMapper.updateClient(client);
        if(i == 0 ) return Result.create(ResultEnum.UPDATE_ERROR,client);
        else return Result.create(ResultEnum.UPDATE_SUCCESS,client);
    }

    @Override
    public Result showClient(Integer pageNo, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNo,pageSize);
        List<Client> clients = clientMapper.showClient(keyword);
        PageInfo<Client> pageInfo = new PageInfo<>(clients);
        return Result.create(ResultEnum.QUERY_SUCCESS,pageInfo);
    }


    @Override
    public Result queryClientVehicles(Integer clientId) {
        List<Vehicle> list = vehicleMapper.getListByCId(clientId);
        if(list == null) Result.create(ResultEnum.UNKNOWN_ERROR, null);
        return Result.create(ResultEnum.QUERY_SUCCESS, list);
    }
}
