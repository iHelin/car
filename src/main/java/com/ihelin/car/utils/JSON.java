package com.ihelin.car.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JavaType;

public class JSON {

	private static JsonMapper mapper = new JsonMapper();

	public static String toJson(Object object) {
		return mapper.toJson(object);
	}

	public static <T> T parseObject(String jsonString, Class<T> clazz) {
		return mapper.fromJson(jsonString, clazz);
	}

	public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
		JavaType jt = mapper.createCollectionType(List.class, clazz);
		return mapper.fromJson(jsonString, jt);
	}

	public static <T> Set<T> parseSet(String jsonString, Class<T> clazz) {
		JavaType jt = mapper.createCollectionType(Set.class, clazz);
		return mapper.fromJson(jsonString, jt);
	}

	public static <K, V> Map<K, V> parseMap(String jsonString, Class<K> keyclz, Class<V> valclz) {
		JavaType jt = mapper.createCollectionType(Map.class, keyclz, valclz);
		return mapper.fromJson(jsonString, jt);
	}
}
