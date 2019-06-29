package com.nc.ncbackend.repository;


import com.nc.ncbackend.pojo.DemoData;
import org.springframework.data.repository.CrudRepository;

public interface DemoDataRepository extends CrudRepository<DemoData, Long> {
}
