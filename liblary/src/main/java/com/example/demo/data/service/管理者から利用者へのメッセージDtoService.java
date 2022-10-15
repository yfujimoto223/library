package com.example.demo.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.dto.管理者から利用者へのメッセージDto;
import com.example.demo.data.repository.管理者から利用者へのメッセージRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class 管理者から利用者へのメッセージDtoService {
	@Autowired
	private 管理者から利用者へのメッセージRepository 管理者から利用者へのメッセージRepository;

	/**メッセージIDを検索キーとして管理者から利用者へのメッセージDBを検索**/
	public Optional<管理者から利用者へのメッセージDto> findOne(Long メッセージID) {
		return 管理者から利用者へのメッセージRepository.findById(メッセージID);

	}

	/**予約者IDを検索キーとして管理者から利用者へのメッセージDBを検索**/
	public List<管理者から利用者へのメッセージDto> findBy予約者Id(Long 予約者Id) {
		return 管理者から利用者へのメッセージRepository.findAll().stream().filter(a -> a.get予約者ID().equals(予約者Id)).toList();

	}

	/**管理者から利用者へのメッセージDTOの全データを検索**/
	public List<管理者から利用者へのメッセージDto> findAll() {
		return 管理者から利用者へのメッセージRepository.findAll();

	}

	public 管理者から利用者へのメッセージDto save(管理者から利用者へのメッセージDto 管理者から利用者へのメッセージDto) {
		return 管理者から利用者へのメッセージRepository.save(管理者から利用者へのメッセージDto);
	}

	public 管理者から利用者へのメッセージDto update(管理者から利用者へのメッセージDto 管理者から利用者へのメッセージDto) {
		return 管理者から利用者へのメッセージRepository.save(管理者から利用者へのメッセージDto);
	}

	public void delete(管理者から利用者へのメッセージDto 管理者から利用者へのメッセージDto) {
		管理者から利用者へのメッセージRepository.delete(管理者から利用者へのメッセージDto);
	}

}
