package com.example.demo.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.example.demo.entity.Classs;
import com.example.demo.reponsitory.ClassRepository;

@Service
public class ClassService {
	@Resource
	private ClassRepository classRepository;

	@Transactional
	public void save(Classs classs) {

		classRepository.save(classs);
	}
	@Transactional
	public void deleteByCode(int code) {

		classRepository.deleteByCode(code);
	}
	
	@Transactional
	public void updateClass(Classs classs) {

		Classs cl=classRepository.findByCode(classs.getCode());
		cl.setClassname(classs.getClassname());
		classRepository.saveAndFlush(cl);
	}
	
	public List<Classs> findAll() {
		
		return classRepository.findAll();
	}
	public int nullfirst() {
		System.out.println("nn");
		long nums = classRepository.count();
		for (int i = 1; i <= (int) nums; i++) {
			if (classRepository.findByCode(i) == null) {
				return i;
			}
		}
		return (int) (nums + 1);
	}
	public Classs findByCode(int code) {

		return classRepository.findByCode(code);
	}
	public Classs findByClassname(String classname) {
		
		return classRepository.findByClassname(classname);
	}
	
}
