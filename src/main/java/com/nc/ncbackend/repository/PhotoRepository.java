package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
