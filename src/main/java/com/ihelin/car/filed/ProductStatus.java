package com.ihelin.car.filed;

public enum ProductStatus {
	UPSHELF(1), // 上架
	DOWNSHELF(2);// 下架

	private Integer value;

	ProductStatus(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

}
