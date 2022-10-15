package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.出版社Dto;

@Repository
public interface 出版社Repository extends JpaRepository<出版社Dto, Long> {

	
}
