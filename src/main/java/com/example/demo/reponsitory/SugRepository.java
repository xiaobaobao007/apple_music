package com.example.demo.reponsitory;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.suggest;

public interface SugRepository extends JpaRepository<suggest, Integer> {

	suggest findByid(int id);
}