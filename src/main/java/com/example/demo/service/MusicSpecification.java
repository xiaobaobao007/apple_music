package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.Matchmusic;
import com.example.demo.entity.Music;

public class MusicSpecification {
	
	public static Specification<Music> QueryMusic(Music music,Matchmusic matchmusic){
		
		return new Specification<Music>(){
			
			private static final long serialVersionUID = 1L;

			//root：查询哪个表，query：查询哪些字段和排序是什么，cb：字段之间是什么关系，如何生成查询条件，每一个查询条件什么方式
			@Override
			public Predicate toPredicate(Root<Music> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates=new ArrayList<Predicate>();
				if(music!=null){
					if(music.getNumber()!=null && music.getNumber()>0){
						predicates.add(cb.equal(root.get("number"),music.getNumber()));
					}
					if(!StringUtils.isEmpty(music.getName())){
						predicates.add(cb.like(root.get("name"),"%"+music.getName()+"%"));
					}
					if(!StringUtils.isEmpty(music.getSinger())){
						predicates.add(cb.like(root.get("singer"),"%"+music.getSinger()+"%"));
					}
					if(!StringUtils.isEmpty(music.getSrc())){
						predicates.add(cb.like(root.get("src"),"%"+music.getSrc()+"%"));
					}
					if(!StringUtils.isEmpty(music.getImage())){
						predicates.add(cb.like(root.get("image"),"%"+music.getImage()+"%"));
					}
					if(music.getClicks()!=null && music.getClicks()>0){
						predicates.add(cb.equal(root.get("clicks"),music.getClicks()));
					}
					if(matchmusic.getMinclicks()!=null&&matchmusic.getMaxclicks()==null) {
						predicates.add(cb.ge(root.get("clicks"),matchmusic.getMinclicks()));
					}
					if(matchmusic.getMinclicks()==null&&matchmusic.getMaxclicks()!=null) {
						predicates.add(cb.le(root.get("clicks"),matchmusic.getMaxclicks()));
					}
					if(matchmusic.getMinclicks()!=null&&matchmusic.getMaxclicks()!=null) {
						predicates.add(cb.between(root.get("clicks"),matchmusic.getMinclicks(),matchmusic.getMaxclicks()));
					}
					if(music.getClasss()!=null && !StringUtils.isEmpty(music.getClasss().getClassname())){
						root.join("classs",JoinType.INNER);
						predicates.add(cb.equal(root.get("classs").get("classname"),music.getClasss().getClassname()));
					}
					if(music.getClasss()!=null && music.getClasss().getCode()>0){
						root.join("classs",JoinType.INNER);
						predicates.add(cb.equal(root.get("classs").get("code"),music.getClasss().getCode()));
					}
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}