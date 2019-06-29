package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.CheckCondition;
import com.nc.ncbackend.repository.CheckConditionRepository;
import com.nc.ncbackend.service.CheckConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckConditionServiceImpl implements CheckConditionService {

    @Autowired
    CheckConditionRepository checkConditionRepository;

    @Override
    public void save(CheckCondition checkCondition) {
        checkConditionRepository.save(checkCondition);
    }
}
