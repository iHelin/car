package com.ihelin.car.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;

@Repository
public class DataConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataConfig.class);
	private static Map<String, Object> configMap = Maps.newHashMap();
	public static final String fileName = "mall_config.yml";

	@SuppressWarnings("unchecked")
	@PostConstruct
	public static void getMap() {
		File file = new File(CommonConfig.getWebInfDir(), fileName);
		try {
			configMap = Yaml.loadType(file, HashMap.class);
		} catch (Exception e) {
			LOGGER.warn("loading throws exception, file path: " + fileName + ", errMsg: " + e.getMessage());
		}
	}

	public static Object getValue(String key) {
		return configMap.get(key);
	}

	public static void setValue(String name, Object value) {
		configMap.put(name, value);
		File file = new File(CommonConfig.getWebInfDir(), fileName);
		try {
			Yaml.dump(configMap, file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
