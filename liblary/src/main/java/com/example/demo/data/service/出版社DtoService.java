package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.出版社Dto;
import com.example.demo.data.repository.出版社Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 出版社DtoService {
	@Autowired
	private 出版社Repository 出版社Repository;

	/**出版社IDを検索キーとして出版社DBを検索**/
	public Optional<出版社Dto> findOne(Long 出版社Id) {
		return 出版社Repository.findById(出版社Id);
	}

	/**出版社DTOを検索キーとして出版社DBを検索**/
	public Optional<出版社Dto> findOne(出版社Dto 出版社Dto) {
		return 出版社Repository.findById(出版社Dto.get出版社ID());
	}

	/**全出版社DBを検索**/
	public List<出版社Dto> findAll() {
		return 出版社Repository.findAll();
	}

	/**出版社DBへ保存**/
	public 出版社Dto save(出版社Dto 出版社Dto) {
		return 出版社Repository.save(出版社Dto);
	}
}
