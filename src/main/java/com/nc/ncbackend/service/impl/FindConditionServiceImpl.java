package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.FindCondition;
import com.nc.ncbackend.repository.FindConditionRepository;
import com.nc.ncbackend.service.FindConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindConditionServiceImpl implements FindConditionService {

    @Autowired
    FindConditionRepository findConditionRepository;

    @Override
    public void save(FindCondition findCondition) {
        findConditionRepository.save(findCondition);
    }
}
