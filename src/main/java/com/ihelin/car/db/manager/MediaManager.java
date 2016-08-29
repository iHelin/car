package com.ihelin.car.db.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ihelin.car.model.Item;
import com.ihelin.car.utils.WechatUtil;

@Service
public class MediaManager {

	public List<Item> selectMediaByPage(String type,String accessToken, int offset, int size) {
		return WechatUtil.selectMediaByPage(type,accessToken, offset, size);
	}
	
	public int selectMediaCount(String accessToken,String type){
		return WechatUtil.selectMediaCount(accessToken, type);
	}

	public static class PostData {
		public String type;
		public int offset;
		public int count;

		public PostData(String type, int offset, int count) {
			this.type = type;
			this.offset = offset;
			this.count = count;
		}
	}

}
