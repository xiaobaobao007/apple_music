package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.demo.entity.Classs;

@Entity
//用于持久化类，自动创建
@Table(name="tb_music")
public class Music{
	
	@Id
	@Column(name = "id")
	private int id;
	private int clloect;
	private Integer number;
	private String name;
	private String singer;
	private String src;
	private String image;
	private Integer clicks;
	
	@ManyToOne(
			fetch=FetchType.LAZY,
			targetEntity=Classs.class,
			cascade=CascadeType.PERSIST
			)
	//Music的classsId对应关联Classs的code
	@JoinColumn(name="classsId",referencedColumnName="code")
	private Classs classs;
	@ManyToMany(mappedBy="musics")
	private List<FKUser> users;
	
	public Classs getClasss() {
		return classs;
	}
	public void setClasss(Classs classs) {
		this.classs = classs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public int getClloect() {
		return clloect;
	}
	public void setClloect(int clloect) {
		this.clloect = clloect;
	}
	
	public Integer getClicks() {
		return clicks;
	}
	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Music()
	{
		super();
	}
	public Music(int id,Integer number,String name,String singer,String src,Integer clicks,String image,Classs classs)
	{
		super();
		this.id=id;
		this.number=number;
		this.name=name;
		this.singer=singer;
		this.src=src;
		this.clicks=clicks;
		this.image=image;
		this.classs=classs;
	}
	public String toString() {
		return "Music [number"+number+",name=" + name + ", singer=" + singer + ", src=" + src + ", clicks=" + clicks + ", image=" + image + "]";
	}
	public List<FKUser> getUsers() {
		return users;
	}
	public void setUsers(List<FKUser> users) {
		this.users = users;
	}
}
