package com.shu.carsystem.service;

import com.shu.carsystem.common.Result;
import com.shu.carsystem.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ClientService {
    Client inquireClient(Client client);

    int insertClient(Client client);

    int updatePasswordById(Integer id, String password);

    int updateInfoById(Integer id, String name, String nature, String contact, String phone);

    Result queryClientVehicles(Integer clientId);

    Result addClientInfo(Map<String, Object> map);

    Result queryOngoingHistory(Integer clientId, Integer pageNo, Integer pageSize, String keyWord);

    Result queryFinishedHistory(Integer clientId, Integer pageNo, Integer pageSize, String keyWord);

    Result showClient(Integer pageNo,Integer pageSize,String keyword);

    Result updateClient(Client client);

}
