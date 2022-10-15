package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.管理者Dto;

@Repository
public interface 管理者Repository extends JpaRepository<管理者Dto, Long> {

	
}
