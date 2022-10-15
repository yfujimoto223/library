package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.書籍Dto;

@Repository
public interface 書籍Repository extends JpaRepository<書籍Dto, Long> {

	
}
