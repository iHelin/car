package com.ihelin.car.controller.admin;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ihelin.car.db.entity.Product;
import com.ihelin.car.db.plugin.Pagination;
import com.ihelin.car.filed.ProductStatus;
import com.ihelin.car.utils.ResponseUtil;

@Controller
public class AdminProductController extends BaseAdminController {

	@RequestMapping("product_admin")
	public String productAdmin(Model model, Integer pageNum, String productName, Integer status) {
		if (pageNum == null)
			pageNum = 1;
		List<Product> products = productManager.listProductByCondition(productName, status, (pageNum - 1) * PAGE_LENGTH,
				PAGE_LENGTH);
		int totalCount = productManager.listProductCount(productName, status);
		model.addAttribute("products", products);
		model.addAttribute("pagination", new Pagination(totalCount, pageNum, PAGE_LENGTH));
		model.addAttribute("productName", productName).addAttribute("status", status);
		return ftl("product_admin");
	}

	@RequestMapping("product_admin_edit")
	public String productEdit(Integer productId, Model model) {
		Product product = productManager.selectProductbyId(productId);
		model.addAttribute("product", product);
		return ftl("product_edit");
	}

	@RequestMapping("product_admin_edit.do")
	public void productAdminEdit(Product product, HttpServletResponse response) {
		if (product.getIsFreePostage() == null)
			product.setIsFreePostage(false);
		if (product.getId() == null) {
			if (product.getSellCount() == null)
				product.setSellCount(0);
			product.setCreateTime(new Date());
			productManager.insert(product);
		} else {
			Product oldProduct = productManager.selectProductbyId(product.getId());
			oldProduct.setName(product.getName());
			oldProduct.setPrice(product.getPrice());
			oldProduct.setStock(product.getStock());
			oldProduct.setIsFreePostage(product.getIsFreePostage());
			oldProduct.setBargin(product.getBargin());
			oldProduct.setPromo(product.getPromo());
			oldProduct.setStatus(product.getStatus());
			oldProduct.setImg(product.getImg());
			oldProduct.setDetail(product.getDetail());
			productManager.update(oldProduct);
		}
		ResponseUtil.writeSuccessJSON(response);
	}

	@RequestMapping("delete_product")
	public void deleteProduct(Integer id, HttpServletResponse response) {
		productManager.deleteProductById(id);
		ResponseUtil.writeSuccessJSON(response);
	}

	@RequestMapping("up_down_product")
	public void upDownProduct(Integer id, HttpServletResponse response) {
		Product product = productManager.selectProductbyId(id);
		if (product == null) {
			ResponseUtil.writeFailedJSON(response, "item_is_null");
			return;
		}
		if (product.getStatus().equals(ProductStatus.UPSHELF.getValue())) {
			product.setStatus(ProductStatus.DOWNSHELF.getValue());
		} else if (product.getStatus().equals(ProductStatus.DOWNSHELF.getValue())) {
			product.setStatus(ProductStatus.UPSHELF.getValue());
		}
		productManager.update(product);
		ResponseUtil.writeSuccessJSON(response);
	}

}
