package com.shu.carsystem.service.Impl;

import com.shu.carsystem.mapper.ProjectMapper;
import com.shu.carsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
}
