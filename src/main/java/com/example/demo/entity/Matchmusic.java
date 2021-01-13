package com.example.demo.entity;

public class Matchmusic {
	
	Integer minclicks;
	Integer maxclicks;
	Matchmusic(){
		super();
	}
	Matchmusic(Integer minclicks,Integer maxclicks){
		super();
		this.minclicks=minclicks;
		this.maxclicks=maxclicks;
	}
	public Integer getMinclicks() {
		return minclicks;
	}
	public void setMinclicks(Integer minclicks) {
		this.minclicks = minclicks;
	}
	public Integer getMaxclicks() {
		return maxclicks;
	}
	public void setMaxclicks(Integer maxclicks) {
		this.maxclicks = maxclicks;
	}
	public String toString() {
		return "min:"+this.minclicks+",max:"+this.maxclicks;
	}
}
