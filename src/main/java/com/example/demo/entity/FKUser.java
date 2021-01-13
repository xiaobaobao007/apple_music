package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name = "tb_us")
@Entity
public class FKUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	private Integer downloadnums;
	private String username;
	private String loginName;
	private String password;
	@ManyToMany(cascade = {CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_role", 
			joinColumns = { @JoinColumn(name = "user_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "role_id") })
	private List<FKRole> roles;
	@ManyToMany
	@JoinTable(name = "tb_user_music")
	private List<Music> musics;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setRoles(List<FKRole> roles) {
		this.roles = roles;
	}
	public List<FKRole> getRoles() {
		return roles;
	}
	@Override
	public String toString() {
		return "FKUser [id=" + id + ", loginName=" + loginName + ", username=" + username
				+ ", password=" + password + ", roles=" + roles + "]";
	}

	public List<Music> getMusics() {
		return musics;
	}

	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}

	public Integer getDownloadnums() {
		return downloadnums;
	}

	public void setDownloadnums(Integer downloadnums) {
		this.downloadnums = downloadnums;
	}
}
