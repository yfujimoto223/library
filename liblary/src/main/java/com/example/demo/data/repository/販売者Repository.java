package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.販売者Dto;

@Repository
public interface 販売者Repository extends JpaRepository<販売者Dto, Long> {

	
}
