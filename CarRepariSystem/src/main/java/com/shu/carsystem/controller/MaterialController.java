package com.shu.carsystem.controller;

import com.shu.carsystem.common.Result;
import com.shu.carsystem.entity.Material;
import com.shu.carsystem.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @PostMapping
    public Result insert(@RequestBody Material material){
        return materialService.insert(material);
    }
}
