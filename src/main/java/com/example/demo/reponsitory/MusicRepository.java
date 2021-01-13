package com.example.demo.reponsitory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Music;
import com.example.demo.entity.Classs;

public interface MusicRepository extends JpaRepository<Music, Integer>,JpaSpecificationExecutor<Music> {

	@SuppressWarnings("unchecked")
	Music save(Music music);
	List<Music> findAll();
	long count();
	Music findByNumber(Integer number);//确保音乐编号唯一
	Page<Music> findByClasss(Classs classs,Pageable page);
	void deleteByNumber(Integer number);
//	@Query(value = "select s from Music s where s.classs.classname = ?code")//
//	@Query(value = "select s from Music s where s.classs.classname = :code")
//	@Query(value = "select s from Music s where s.classs.classname = ?1")
//	List<Music> findBycodeMatch(@Param("code") int code);
//	@Modifying
//	@Query(value = "SELECT MAX(number) as number FROM tb_music",nativeQuery=true)
//	int findmaxnumber();
//	@Query(value = "SELECT MAX(clicks) as ckicks FROM tb_music",nativeQuery=true)
//	int findmaxclicks();
}
//http://localhost:8080/findmusicMatchs?classname=%E7%AC%AC%E4%B8%80%E7%B1%BB&fnumber=0&lnumber=999&fclicks=0&lclicks=999




