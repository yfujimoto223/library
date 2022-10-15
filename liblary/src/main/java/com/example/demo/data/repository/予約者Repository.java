package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.予約者Dto;

@Repository
public interface 予約者Repository extends JpaRepository<予約者Dto, Long> {

	
}
