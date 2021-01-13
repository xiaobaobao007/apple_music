package com.example.demo.reponsitory;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Classs;

public interface ClassRepository extends JpaRepository<Classs, Integer> {

	Classs findByCode(int code);

	Classs findByClassname(String classname);

	void deleteByCode(int code);

	List<Classs> findAll();
}