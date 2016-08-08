package com.ihelin.car.db.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ihelin.car.db.entity.Product;
import com.ihelin.car.db.mapper.ProductMapper;

@Service
public class ProductManager {

	@Resource
	private ProductMapper productMapper;

	public Product selectProductbyId(Integer id) {
		return productMapper.selectByPrimaryKey(id);
	}

	public int insert(Product product) {
		return productMapper.insert(product);
	}

	public int update(Product product) {
		return productMapper.updateByPrimaryKey(product);
	}

	public int deleteProductById(Integer id) {
		return productMapper.deleteByPrimaryKey(id);
	}

	public List<Product> listProductByCondition(String productName, Integer status, int offset, int size) {
		Map<String, Object> res = Maps.newHashMap();
		if (StringUtils.isNotBlank(productName))
			res.put("productName", productName);
		if (status != null)
			res.put("status", status);
		return productMapper.listProductByCondition(res, new RowBounds(offset, size));
	}

	public int listProductCount(String productName, Integer status) {
		Map<String, Object> res = Maps.newHashMap();
		if (StringUtils.isNotBlank(productName))
			res.put("productName", productName);
		if (status != null)
			res.put("status", status);
		return productMapper.listProductCount(res);
	}

}
