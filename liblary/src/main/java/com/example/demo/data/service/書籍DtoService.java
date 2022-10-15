package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.書籍Dto;
import com.example.demo.data.repository.書籍Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 書籍DtoService {
	@Autowired
	private 書籍Repository 書籍Repository;

	/**書籍IDを検索キーとして書籍DBを検索**/
	public Optional<書籍Dto> findOne(Long 書籍Id) {
		return 書籍Repository.findById(書籍Id);
	}

	/**書籍DTOを検索キーとして書籍DBを検索**/
	public Optional<書籍Dto> findOne(書籍Dto 書籍Dto) {
		return 書籍Repository.findById(書籍Dto.get書籍ID());
	}

	/**書名を検索キーとして書籍DBを検索**/
	public Optional<書籍Dto> findOneTitleName(String titleName) {
		return 書籍Repository.findAll().stream().filter(a -> a.get書名().equals(titleName)).findFirst();
	}

	/**全書籍DBを検索**/
	public List<書籍Dto> findAll() {
		return 書籍Repository.findAll();
	}

	/**書籍を保存**/
	public 書籍Dto save(書籍Dto 書籍Dto) {
		return 書籍Repository.save(書籍Dto);
	}

}
