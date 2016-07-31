package com.ihelin.car.db.entity;

import java.util.Date;

public class AccessToken {
	private Integer id;

	private String token;

	private Date genTime;

	private Date expiresTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	// 距离过期毫秒数
	public long getLeftValidTimeMillis() {
		return this.expiresTime.getTime() - System.currentTimeMillis();
	}
}