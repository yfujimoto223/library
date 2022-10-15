package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.注文Dto;

@Repository
public interface 注文Repository extends JpaRepository<注文Dto, Long> {

	
}
