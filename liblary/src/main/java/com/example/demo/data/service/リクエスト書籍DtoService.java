package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.リクエスト書籍Dto;
import com.example.demo.data.repository.リクエスト書籍Repository;

@Service
@Transactional(rollbackFor = Exception.class)
public class リクエスト書籍DtoService {
	@Autowired
	private リクエスト書籍Repository リクエスト書籍Repository;

	/**リクエストIDを検索キーとして書籍DBを検索**/
	public Optional<リクエスト書籍Dto> findOne(Long リクエストId) {
		return リクエスト書籍Repository.findById(リクエストId);
	}

	/**全リクエスト書籍DBを検索**/
	public List<リクエスト書籍Dto> findAll() {
		return リクエスト書籍Repository.findAll();
	}

	/**リクエスト書籍DBへ保存**/
	public リクエスト書籍Dto save(リクエスト書籍Dto リクエスト書籍Dto) {
		return リクエスト書籍Repository.save(リクエスト書籍Dto);
	}

}
