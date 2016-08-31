package com.ihelin.car.db.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class OrderManager {
	private static Integer orderIndex = 100;

	public static String createOrderId() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String orderId = sdf.format(date);
		synchronized (OrderManager.class) {
			if (orderIndex > 99)
				orderIndex = 0;
			orderId = orderId + (orderIndex > 9 ? orderIndex : ("0" + orderIndex));
			orderIndex++;
		}
		return orderId;
	}

}
