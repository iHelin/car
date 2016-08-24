package com.ihelin.car.message.req;

public class LocationMessage extends BaseMessage {
	private String Location_X;// 纬度
	private String Location_Y;// 经度
	private Integer Scale;// 地图缩放大小
	private String Label;// 地理位置信息

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public Integer getScale() {
		return Scale;
	}

	public void setScale(Integer scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

}
