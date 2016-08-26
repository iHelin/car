package com.ihelin.car.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class DataConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataConfig.class);
	private static Map<String, Object> configMap = Maps.newHashMap();

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getValue() {
		File file = new File(CommonConfig.getWebInfDir(), getFileName());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				LOGGER.warn("create file throws exception" + e.getMessage());
				return Maps.newHashMap();
			}
		}
		try {
			configMap = Yaml.loadType(file, HashMap.class);
		} catch (Exception e) {
			LOGGER.warn("loading throws exception, file path: " + getFileName() + ", errMsg: " + e.getMessage());
			return Maps.newHashMap();
		}
		return configMap;
	}

	public static void setValue(String name, Object value) {
		configMap.put(name, value);
		File file = new File(CommonConfig.getWebInfDir(), getFileName());
		try {
			Yaml.dump(configMap, file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		File dumpFile = new File("/Users/ihelin/git/lube-mall/src/main/webapp/WEB-INF", getFileName());
		Map<String, Object> res = Maps.newHashMap();
		double value = 88;
		res.put("unionpostage", value);
		Map<String, Object> configMap;
		try {
			Yaml.dump(res, dumpFile);
			configMap = Yaml.loadType(dumpFile, HashMap.class);
			for (Object key : configMap.keySet()) {
				System.out.println(key + ":" + configMap.get(key).toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static String getFileName() {
		return "mall_config.yml";
	}

}
