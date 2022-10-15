package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.貸出状況Dto;

@Repository
public interface 貸出状況Repository extends JpaRepository<貸出状況Dto, Long> {

	
}
