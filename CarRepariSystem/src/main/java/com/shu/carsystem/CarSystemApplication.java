package com.shu.carsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.shu.carsystem.mapper")
public class CarSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarSystemApplication.class, args);
    }

}
