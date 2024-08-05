package com.javateam.SpringJPA2.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
// import org.springframework.stereotype.Repository;

import com.javateam.SpringJPA2.domain.TestVO;

// @Repository
public interface TestRepository extends CrudRepository<TestVO, Long> {
	
	List<TestVO> findAll();
}