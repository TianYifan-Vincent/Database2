package com.shu.carsystem.pojo;

import com.shu.carsystem.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientIdUser {
    private Integer userId;
    private String clientId2;
    private String password;
    private String phone;
    private Integer isRepairman;
    private String status;

    public ClientIdUser(User user){
        userId = user.getUserId();
        clientId2 = user.getName();
        password = user.getPassword();
        phone = user.getPhone();
        isRepairman = user.getIsRepairman();
        status = user.getStatu();
    }
}
