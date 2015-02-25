package com.smr.simulation.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EntitySimulation{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int[] value;
	private int  key;
	private String user;
	private Date timestamp;
	@SuppressWarnings("unused")
	private long simulationCount;
	
	public EntitySimulation(){
	}
	
	public int[] getValue(){
		return value;
	}
	
	public void setValue(int[] value){
		this.value = value; 
	}
	
	public int geKey(){
		return key;
	}
	
	public void setKey(int key){
		this.key = key; 
	}
	
	
	public void setUser(String user){
		this.user = user;
	}
	
	public String getUser(){
		return this.user;
	}
	
	public void setTimestamp(Date timestamp){
		this.timestamp = timestamp;
	}
	
	public Date getTimestamp(){
		return this.timestamp;
	}
	
	
}