package com.ihelin.car.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.ihelin.car.config.CommonConfig;

/**
 * 支持 bmp jpg gif png 等输入格式
 * 
 * @author Frank
 *
 */
public class ImageUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

	public static final float QUALITY_SUPER = 1f;
	public static final float QUALITY_HIGH = 0.95f;
	public static final float QUALITY_MEDIA = 0.5f;
	public static final float QUALITY_LOW = 0.25f;
	
	public static final int DEFAULT_WIDTH = 850;
	
	public static final int SMALL_WIDTH = 200;
	
	private static final String UPLOAD_FOLDER = "upload";
	
	private static final int MAX_FILE_SIZE = 5 * 1024 * 1024;
	
	public static class CompressedImage {
		private byte[] data;
		private int width;
		private int height;
		public byte[] getData() {
			return data;
		}
		public void setData(byte[] data) {
			this.data = data;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
	}

	/**
	 * 压缩图片为JPEG格式，默认高质量压缩
	 * 
	 * @param inputImgData
	 *            输入图
	 * @param w
	 *            目标宽 -1, 则与高度保持源高宽比
	 * @param h
	 *            目标高 -1, 则与宽度保持源高宽比
	 * @param per
	 *            压缩清晰度
	 * @return
	 * @throws IOException
	 */
	
	public static byte[] compressImage(byte[] inputImgData, int width) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(inputImgData);
		
		return compressImage(in, width);
	}
	
	public static byte[] compressImage(InputStream in, int width) throws IOException {
		BufferedImage inputImg = ImageIO.read(in);
		if (inputImg == null) {
			throw new IllegalArgumentException("unsupported file format");
		}
		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		try {
			compress(inputImg, outs, width, QUALITY_HIGH);
		} finally {
			try {
				outs.close();
				in.close();
			} catch (Exception e) {
				
			}
		}
		return outs.toByteArray();
	}

	
	public static CompressedImage compress(byte[] inputImgData, int width) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(inputImgData);
		
		return compress(in, width);
	}
	
	public static CompressedImage compress(InputStream in, int maxWidth) throws IOException {
		BufferedImage inputImg = ImageIO.read(in);
		if (inputImg == null) {
			throw new IllegalArgumentException("unsupported file format");
		}
		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		try {
			CompressedImage cimg = new CompressedImage();
            cimg.width = inputImg.getWidth();
            cimg.height = inputImg.getHeight();
			if (cimg.width > maxWidth ||cimg.height > maxWidth) {
				cimg = compress(inputImg, outs, maxWidth, QUALITY_HIGH);
				cimg.data = outs.toByteArray();
			} else {
				in.reset();	//reset pointer
				cimg.data = IOUtils.toByteArray(in);
			}
			
			return cimg;
		} finally {
			try {
				outs.close();
				in.close();
			} catch (Exception e) {
				LOGGER.warn("compress: close stream throws exception.");
			}
		}
	}

	/**
	 * 按指定尺寸压缩图片，压缩结果与宽度保持源宽高比
	 * 
	 * @param inputImg
	 *            输入图
	 * @param outs
	 *            输出流
	 * @param w
	 *            目标宽, 若大于源图宽度，则保持源尺寸
	 * @param quality
	 *            清晰度
	 */
	private static CompressedImage compress(BufferedImage inputImg, OutputStream outs,
			int w, float quality) {
		int h = -1;
		try {
			// 得到源图宽
			int oldWidth = inputImg.getWidth();
			// 得到源图高
			int oldHeight = inputImg.getHeight();
			
            // 根据原图的大小生成空白画布  
            BufferedImage tempImg = new BufferedImage(oldWidth, oldHeight,  
                    BufferedImage.TYPE_INT_RGB);  
            Image canvasImg = tempImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            
            // 在新的画布上生成原图的缩略图  
            Graphics2D g = tempImg.createGraphics();  
            g.setColor(Color.white);  
            g.fillRect(0, 0, oldWidth, oldHeight);  
            g.drawImage(inputImg, 0, 0, oldWidth, oldHeight, Color.white, null);  
            g.dispose();
            
            int canvasWidth = canvasImg.getWidth(null);
            int canvasHeight = canvasImg.getHeight(null);
            
            BufferedImage newImg = null;
            if (canvasWidth >= oldWidth) {
            	newImg = tempImg;
            } else {
            	newImg = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);  
            	newImg.getGraphics().drawImage(canvasImg, 0, 0, null);
            }
            
            ImageIO.write(newImg, "jpeg" , outs );  
            
            outs.close();
            
            CompressedImage cimg = new CompressedImage();
            cimg.width = newImg.getWidth();
            cimg.height = newImg.getHeight();
 
            return cimg;
		} catch (IOException ex) {
			LOGGER.warn("compress: ImageIO.write throws exception.", ex);
		}
		return null;
	}
	
	public static byte[] cropImage(String realPath, String imgUrl, String contextPath, int imgW, int imgH, 
			int imgX1, int imgY1, int cropW, int cropH) throws IOException {
		
		//如果是部署在服务器上的情况，则需要到webapps/下面的upload目录
		if (StringUtils.isNotBlank(contextPath) || realPath.endsWith("root")) {	
			imgUrl = ".." + imgUrl;
		}
			
		BufferedImage inputImg = ImageIO.read(new File(realPath, imgUrl));

		//表示比例值(float)inputImg.getWidth() / imgW;
		float scale = 1;
		
		int imgwidth = (int)(cropW * scale);
		int imgheight = (int)(cropH*scale);
		
		BufferedImage scaleImage = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);  
		Graphics g = scaleImage.getGraphics();  
		g.drawImage(inputImg.getScaledInstance(imgW, imgH, Image.SCALE_SMOOTH), 0, 0, null);   
		g.dispose();
		
        BufferedImage newImg = new BufferedImage(imgwidth, imgheight, BufferedImage.TYPE_INT_RGB);
        BufferedImage copyFrom = scaleImage;
        if (imgwidth < scaleImage.getWidth() && imgheight < scaleImage.getHeight()) {
        	copyFrom = scaleImage.getSubimage(imgX1, imgY1, imgwidth, imgheight);
        }
        // 在新的画布上生成原图的缩略图  
        g = newImg.createGraphics();  
        g.drawImage(copyFrom, 0, 0, Color.white, null);  
        g.dispose();
        
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
		try {
			ImageIO.write(newImg, "jpeg" , outs );  
		} finally {
			try {
				outs.close();
			} catch (Exception e) {
				LOGGER.warn("cropImage: close stream throws exception.", e);
			}
		}
		return outs.toByteArray();
	}
	
	/**
	 * @param imageData
	 * @param contextPath
	 * @param realPathOnDisk
	 * @param namespace
	 * @return
	 * @throws IOException
	 */
	
	public static String saveImageFile(byte[] imageData, String contextPath, String realPathOnDisk, String subPath) throws IOException {
		return saveImageFile(imageData, contextPath, realPathOnDisk, subPath,null);
	}
	
	public static String saveImageFile(byte[] imageData, String contextPath, String realPathOnDisk, String subPath,String fileName) throws IOException {
		if(fileName == null)
			fileName = generateFileName(imageData);
		
		if (StringUtils.isBlank(getFileNameSuffix(fileName))) {	
			fileName = fileName + ".jpg";
		}
		String url = "/" + UPLOAD_FOLDER + contextPath + "/" + subPath + "/" + fileName;
		String filePath = url;
		//如果是部署在服务器上的情况，则需要到webapps/下面的upload目录
		if (StringUtils.isNotBlank(contextPath) || realPathOnDisk.endsWith("root")) {	
			filePath = ".." + url;
		}
		File realFile = new File(realPathOnDisk, filePath);
		
		FileUtils.writeByteArrayToFile(realFile, imageData);

		return url;
	}
	
	public static class ImageResult {
		private String url;
		private String fileName;
		private int width;
		private int height;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
	}
	
	/*
	 * 压缩并保存图片
	 */
	public static ImageResult compressAndSave(MultipartFile imgFile, int maxWidth,
			String subPath, boolean genNewFileName) throws IOException {
		
		String fileName = imgFile.getOriginalFilename().toLowerCase();
		
		if (imgFile == null || imgFile.getSize() <= 0) {
			throw new IOException("文件大小不正确。");
		} else if (!(fileName.endsWith("jpg") || fileName.endsWith("jpeg") 
				|| fileName.endsWith("png") || fileName.endsWith("gif"))) {
			throw new IOException("图片格式不支持。");
		} else if (imgFile.getSize() > MAX_FILE_SIZE) {
			throw new IOException("图片过大。");
		}
		
		return compressAndSave(fileName, imgFile.getInputStream(), maxWidth, subPath, genNewFileName);
		
	}
	
	public static ImageResult compressAndSave(String fileName, InputStream is, int maxWidth, String subPath, boolean genNewFileName)
			throws IOException {
		String contextPath = CommonConfig.getContextPath();
		String realPath = CommonConfig.getWebappRoot();
		try {
			CompressedImage cimg = compress(is, maxWidth);
			
			ImageResult result = new ImageResult();
			
			if (genNewFileName) {
				fileName = generateFileName(cimg.data) + "." + getFileNameSuffix(fileName);
			}
			
			String url = saveImageFile(cimg.data, contextPath, realPath, subPath, fileName);
			result.url = url;
			result.fileName = fileName;
			result.width = cimg.width;
			result.height = cimg.height;
			return result;
		} catch (IllegalArgumentException e) {
			throw new IOException("图片格式不正确。");
		}
	}
	
	public static List<String> getImageFileList(HttpServletRequest request, String subPath) {
		String contextPath = request.getContextPath();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		
		String url = "/" + UPLOAD_FOLDER + contextPath + "/" + subPath + "/";
		String filePath = url;

		//如果是部署在服务器上的情况，则需要到webapps/下面的upload目录
		if (StringUtils.isNotBlank(contextPath) || realPath.endsWith("root")) {	
			filePath = ".." + url;
		}
		File dir = new File(realPath, filePath);
		File[] files = dir.listFiles();
		List<String> resultList = new ArrayList<String>();
		if (files != null) {
			List<File> fileList = Arrays.asList(files);
			Collections.sort(fileList, new Comparator<File>() {
	
				@Override
				public int compare(File o1, File o2) {
					long v = o2.lastModified() - o1.lastModified();
					if (v == 0) return 0;	//jdk7中，如果值相同一定要返回0
					return (v > 0?1:-1);
				}
				
			});
			
			for (File file : fileList) {
				resultList.add(url + file.getName());
			}
		}
		return resultList;
	}
	
	/**
	 * 以内容为基准生成随机文件名
	 * 
	 * @return
	 */
	private static String generateFileName(byte[] content) {
		return CryptUtil.md5(content);
	}
	
	private static String getFileNameSuffix(String fileName) {
		int suffixIndex = fileName.lastIndexOf(".");
		if (suffixIndex > 0) {
			return fileName.substring(suffixIndex + 1);
		}
		return "";
	}


}
