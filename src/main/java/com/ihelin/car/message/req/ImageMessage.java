package com.ihelin.car.message.req;

/**
 * 图片消息
 * 
 * @author ihelin
 */
public class ImageMessage extends BaseMessage {
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

}
