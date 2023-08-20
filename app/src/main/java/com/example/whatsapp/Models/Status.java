package com.example.whatsapp.Models;

public class Status {
	private String imageUrl;
	private String name;
	private long time;
	private String profilePhoto;
	
	public Status(String imageUrl, String name, long time) {
		this.imageUrl = imageUrl;
		this.name = name;
		this.time = time;
	}
	

	public Status (String name)
	{
		this.name=name;
	}
	public Status(String name, long time,String imageUrl,String profilePhoto) {
		this.name = name;
		this.time = time;
		this.imageUrl=imageUrl;
		this.profilePhoto=profilePhoto;
	}
	public Status(String name,String profilePhoto)
	{
		this.name=name;
		this.profilePhoto=profilePhoto;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
}
