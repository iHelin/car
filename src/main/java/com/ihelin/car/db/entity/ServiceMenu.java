package com.ihelin.car.db.entity;

public class ServiceMenu {
	public static final int TEXT_MENU = 0;
	public static final int LINK_MENU = 1;
	public static final int PIC_MENU = 2;
	private Integer id;

	private String name;

	private String content;

	private Integer contentType;

	private String menuRule;

	private Integer parentId;

	private Integer sort;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getMenuRule() {
		return menuRule;
	}

	public void setMenuRule(String menuRule) {
		this.menuRule = menuRule;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}