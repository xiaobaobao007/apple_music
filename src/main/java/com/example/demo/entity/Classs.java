package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.example.demo.entity.Music;

@Entity
//用于持久化类，自动创建
@Table(name="tb_class")
public class Classs{
	
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int code;
	private String classname;
	
	@OneToMany(
			fetch=FetchType.LAZY,//musics是否立刻加载
			targetEntity=Music.class,
			mappedBy="classs",
			cascade={CascadeType.PERSIST,CascadeType.REMOVE}
			)
	//存放音乐对象
	private Set<Music> musics=new HashSet<>();
	
	public Set<Music> getMusics() {
		return musics;
	}
	public void setMusics(Set<Music> musics) {
		this.musics = musics;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public Classs()
	{
		super();
	}
	public Classs(String classname)
	{
		super();
		this.classname=classname;
	}
	public String getClassname() {
		return this.classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public String toString() {
		return "Classs [cord:"+code+"classname"+classname+"]";
	}
}
