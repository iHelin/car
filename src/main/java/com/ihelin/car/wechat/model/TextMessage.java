package com.ihelin.car.wechat.model;

public class TextMessage extends BaseMessage {
	private String Content;// 消息内容
	private String MsgId;// 消息ID

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
