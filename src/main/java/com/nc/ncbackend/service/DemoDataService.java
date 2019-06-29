package com.nc.ncbackend.service;

import com.nc.ncbackend.pojo.DemoData;
import com.nc.ncbackend.pojo.Game;
import com.nc.ncbackend.repository.DemoDataRepository;
import com.nc.ncbackend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class DemoDataService {


    @Autowired
    DemoDataRepository demoDataRepository;



    public List<DemoData> getLocalDemoData(){

        List<DemoData> res = Arrays.asList(
                new DemoData(1, "param", LocalDate.of(2017, 4, 10)),
                new DemoData(2, "arahis", LocalDate.of(1945, 1, 11)),
                new DemoData(3, "zemlya", LocalDate.of(2043, 7, 24)),
                new DemoData(4, "varejka", LocalDate.of(1987, 8, 5)),
                new DemoData(5, "dom", LocalDate.of(1874, 3, 7)),
                new DemoData(6, "krona", LocalDate.of(2014, 2, 25)));
        return res;
    }



    public List<DemoData> getDemoData(){
        return (List<DemoData>) demoDataRepository.findAll();
    }

    public void save(DemoData dd){
        demoDataRepository.save(dd);
    }



}
