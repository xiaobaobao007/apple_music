package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.suggest;
import com.example.demo.reponsitory.SugRepository;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class SugService {
	@Resource
	private SugRepository sugRepository;

	@Transactional
	public void save(suggest s) {//suggest s

		s.setId(nullfirst());
		sugRepository.save(s);
	}

	public int nullfirst() {
		long nums = sugRepository.count();
		for (int i = 1; i <= (int) nums; i++) {
			if (sugRepository.findByid(i) == null) {
				return i;
			}
		}
		return (int) (nums + 1);
	}
}
