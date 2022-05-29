package com.shu.carsystem.mapper;

import com.shu.carsystem.entity.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClientMapper {
    Client inquireClient(Client client);

    Client inquireClientByName(String id);

    int insertClient(Client client);

    int updatePasswordById(Integer id, String password);

    int updateInfoById(Integer id, String name, String nature, String contact, String phone);

    List<Client> showClient(@Param("keyword")String keyword);

    int updateClient(Client client);
}
