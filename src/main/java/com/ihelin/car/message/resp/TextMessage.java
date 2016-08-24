package com.ihelin.car.message.resp;

public class TextMessage extends BaseMessage {
	private String Content;// 消息内容

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}