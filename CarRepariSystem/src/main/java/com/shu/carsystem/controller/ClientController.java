package com.shu.carsystem.controller;

import com.shu.carsystem.common.Result;
import com.shu.carsystem.common.ResultEnum;
import com.shu.carsystem.entity.Client;
import com.shu.carsystem.entity.User;
import com.shu.carsystem.entity.Vehicle;
import com.shu.carsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PutMapping
    private Result clientInfoUpdate(@RequestBody Map<String, Object> map){
        // Client页面传来的Put请求总共分为：更改客户的个人信息、更改客户的密码两种。
        int cnt = 0;
        if(map.containsKey("password")){
            // 更改密码，传来的参数为clientId和password，根据id去更新client表中的密码。查到则返回新密码和成功信息、否则返回未知错误
            Integer clientId =(Integer) map.get("clientId");
            String password = (String) map.get("password");
            cnt = clientService.updatePasswordById(clientId, password);
            return (cnt == 1) ? Result.create(ResultEnum.SUCCESS, password) : Result.create(ResultEnum.UNKNOWN_ERROR, null);
        }
        else{
            // 更改个人信息，传来的参数为clientId、name、nature、contact、phone
            Integer clientId =(Integer) map.get("clientId");
            String name = (String) map.get("name");
            String nature = (String) map.get("nature");
            String contact = (String) map.get("contact");
            String phone = (String) map.get("phone");
            cnt = clientService.updateInfoById(clientId, name, nature, contact, phone);
            System.out.println(cnt);
            Map<String , Object> map1 = new HashMap<>();
            map1.put("clientId", clientId);
            map1.put("name", name);
            map1.put("nature", nature);
            map1.put("contact", contact);
            map1.put("phone", phone);
            return (cnt == 1) ? Result.create(ResultEnum.SUCCESS, map1) : Result.create(ResultEnum.UNKNOWN_ERROR, null);
        }
    }

    @PostMapping
    //客户新增信息（新增绑定车辆/新增维修业务）
    private Result addClientInfo(@RequestBody Map<String , Object> map){
        return clientService.addClientInfo(map);
    }

    @GetMapping("/ongoing/{clientId}/{pageNo}/{pageSize}/{keyWord}")
    //客户查看自己进行中的历史委托记录
    //注意需要存在模糊查询功能
    private Result queryOngoingHistory(@PathVariable Integer clientId,
                                       @PathVariable Integer pageNo,
                                       @PathVariable Integer pageSize,
                                       @PathVariable String keyWord)
    {
        return clientService.queryOngoingHistory(clientId, pageNo, pageSize, keyWord);
    }

    @GetMapping("/finished/{clientId}/{pageNo}/{pageSize}/{keyWord}")
    //客户查看自己进行中的历史委托记录
    private Result queryFinishedHistory(@PathVariable Integer clientId,
                                        @PathVariable Integer pageNo,
                                        @PathVariable Integer pageSize,
                                        @PathVariable String keyWord)
    {
        return clientService.queryFinishedHistory(clientId, pageNo, pageSize, keyWord);
    }

    @GetMapping("/{clientId}")
    private Result getClientVehicle(@PathVariable Integer clientId){
        return clientService.queryClientVehicles(clientId);
    }
}
