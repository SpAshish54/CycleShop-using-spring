package com.prodapt.learningnewspring.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.learningnewspring.entity.Cycle;
@Repository
public interface CycleRepository extends CrudRepository<Cycle, Integer>{
	
}