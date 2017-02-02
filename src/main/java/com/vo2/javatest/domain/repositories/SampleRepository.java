package com.vo2.javatest.domain.repositories;

import com.vo2.javatest.domain.entities.SampleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by VO2 on 16/01/2017.
 * Sample Spring Data Crud Repository
 */
public interface SampleRepository extends CrudRepository<SampleEntity, Long> {
    List<SampleEntity> findAllByMessageIgnoreCaseContaining(String message);
}
