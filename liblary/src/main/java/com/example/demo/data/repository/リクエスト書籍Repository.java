package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.リクエスト書籍Dto;

@Repository
public interface リクエスト書籍Repository extends JpaRepository<リクエスト書籍Dto, Long> {

	
}
