package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.dto.管理者から利用者へのメッセージDto;

@Repository
public interface 管理者から利用者へのメッセージRepository extends JpaRepository<管理者から利用者へのメッセージDto, Long> {

}
