package com.ihelin.car.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Random;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {

	public static void main(String[] args) throws IOException {
		int width = 300;
		int height = 300;
		String format = "png";
		String content = "2";
		generateQRCode("/Users/ihelin/Documents/", content, format, width, height);
	}

	/**
	 * 二维码生成工具
	 * 
	 * @param folder
	 *            文件存放位置
	 * @param content
	 *            二维码内容
	 * @param format
	 *            二维码格式
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @return 二维码文件名
	 */
	public static String generateQRCode(String folder, String content, String format, int width, int height) {
		String filePath = new Random().nextInt(1000000) + "." + format;
		HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			Path file = new File(folder, filePath).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}

}
