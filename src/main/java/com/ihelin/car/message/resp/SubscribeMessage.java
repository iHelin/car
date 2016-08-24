package com.ihelin.car.message.resp;

public class SubscribeMessage extends BaseMessage {
	private String Event;// 关注或取关

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

}
