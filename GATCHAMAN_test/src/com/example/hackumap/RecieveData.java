package com.example.hackumap;

public class RecieveData {
	private String name;
	private String ido;
	private String keido;
	private String place;
	
	public void setLat(String lat){
		this.ido = lat;
	}
	public void setLon(String lon){
		this.keido = lon;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setPlace(String place){
		this.place = place;
	}

	public String getLat(){
		return this.ido;
	}
	public String getLon(){
		return this.keido;
	}
	public String getName(){
		return this.name;
	}
	public String getPlace(){
		return this.place;
	}
	
}
