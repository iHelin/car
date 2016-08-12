package com.ihelin.car.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
	private Integer id;

	private String name;

	private BigDecimal price;

	private BigDecimal bargin;

	private String img;

	private Integer sellCount;

	private String detail;

	private Boolean isFreePostage;

	private Integer stock;

	private Date createTime;

	private Integer status;

	private Integer productType;

	private String promo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getBargin() {
		return bargin;
	}

	public void setBargin(BigDecimal bargin) {
		this.bargin = bargin;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Boolean getIsFreePostage() {
		return isFreePostage;
	}

	public void setIsFreePostage(Boolean isFreePostage) {
		this.isFreePostage = isFreePostage;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}
}