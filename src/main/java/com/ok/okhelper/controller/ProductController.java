package com.ok.okhelper.controller;

import com.ok.okhelper.common.PageModel;
import com.ok.okhelper.common.ServerResponse;
import com.ok.okhelper.pojo.dto.ProductCondition;
import com.ok.okhelper.pojo.dto.ProductDto;
import com.ok.okhelper.pojo.po.Product;
import com.ok.okhelper.pojo.vo.ProductsVo;
import com.ok.okhelper.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
*Author:zhangxin_an
*Description:
*Data:Created in 16:36 2018/4/30
*/
@Api(tags = "商品模块")
@RestController
public class ProductController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProductService productService;
	
	@ApiOperation(value = "商品搜索", notes = "指定查询条件搜索")
	@GetMapping("product/search")
	public ServerResponse<PageModel<ProductsVo>> searchProduct(ProductCondition productCondition, PageModel pageModel) {
		logger.info("Enter method searchProduct params:productCondition:" + productCondition + "pageModel:" + pageModel);
		ServerResponse<PageModel<ProductsVo>> serverResponse;
		try {
			PageModel<ProductsVo> productsVoPageModel = productService.getProductsList(productCondition, pageModel);
			serverResponse = ServerResponse.createBySuccess(productsVoPageModel);
		} catch (Exception e) {
			e.printStackTrace();
			serverResponse = ServerResponse.createDefaultErrorMessage(e.getMessage());
		}
		
		
		logger.info("Exit method searchProduct params:" + serverResponse);
		return serverResponse;
	}
	
	/*
	* @Author zhangxin_an 
	* @Date 2018/5/1 15:45
	* @Params [categoryIds, orderBy, pageModel]  
	* @Return com.ok.okhelper.common.ServerResponse<com.ok.okhelper.common.PageModel<com.ok.okhelper.pojo.vo.ProductsVo>>  
	* @Description:分类选择商品
	*/
	@ApiOperation(value = "商品搜索", notes = "通过分类搜索")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "categoryIds", value = "分类数组", required = true, paramType = "form"),
			@ApiImplicitParam(name = "orderBy", value = "排序规则", paramType = "form"),
	})
	@GetMapping("product/category")
	public ServerResponse<PageModel<ProductsVo>> searchProductBycategory(Long[] categoryIds, String orderBy, PageModel pageModel) {
		logger.info("Enter method searchProduct params:categoryId:" + categoryIds + "pageModel:" + pageModel);
		ServerResponse<PageModel<ProductsVo>> serverResponse;
		try {
			PageModel<ProductsVo> productsVoPageModel = productService.getProductsListByCategory(categoryIds, orderBy, pageModel);
			serverResponse = ServerResponse.createBySuccess(productsVoPageModel);
		} catch (Exception e) {
			e.printStackTrace();
			serverResponse = ServerResponse.createDefaultErrorMessage(e.getMessage());
		}
		
		
		logger.info("Exit method searchProduct params:" + serverResponse);
		return serverResponse;
	}
	
	@ApiOperation(value = "商品删除")
	@DeleteMapping("product/{id}")
	public ServerResponse deleteProduct(@PathVariable Long id) {
		logger.info("Enter method deleteProduct params:productCondition:" + id);
		ServerResponse serverResponse;
		try {
			productService.deleteProduct(id);
			serverResponse = ServerResponse.createBySuccessMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			serverResponse = ServerResponse.createDefaultErrorMessage(e.getMessage());
		}
		
		
		logger.info("Exit method deleteProduct params:" + serverResponse);
		return serverResponse;
	}
	
	@ApiOperation(value = "商品添加")
	@PutMapping("product")
	public ServerResponse<Product> addProduct(@Valid ProductDto productDto) {
		logger.info("Enter method addProduct params:productDto:" + productDto);
		
		ServerResponse serverResponse;
		
		try {
			serverResponse = ServerResponse.createBySuccess("添加成功", productService.addProduct(productDto));
		} catch (Exception e) {
			e.printStackTrace();
			serverResponse = ServerResponse.createDefaultErrorMessage(e.getMessage());
		}
		
		
		logger.info("Exit method addProduct params:" + serverResponse);
		return serverResponse;
	}
	
	@ApiOperation(value = "商品修改")
	@PostMapping("product")
	public ServerResponse<Product> updateProduct(ProductDto productDto) {
		logger.info("Enter method addProduct params:productDto:" + productDto);
		ServerResponse serverResponse;
		try {
			serverResponse = ServerResponse.createBySuccess("修改成功", productService.updateProduct(productDto));
		} catch (Exception e) {
			e.printStackTrace();
			serverResponse = ServerResponse.createDefaultErrorMessage(e.getMessage());
		}
		
		
		logger.info("Exit method addProduct params:" + serverResponse);
		return serverResponse;
	}
	
	
	@ApiOperation(value = "查询单个商品")
	@GetMapping("product/{id}")
	public ServerResponse getProduct(@PathVariable Long id) {
		logger.info("Enter method getProduct params:id:" + id);
		ServerResponse serverResponse;
		try {
			Product products = productService.getProduct(id);
			serverResponse = ServerResponse.createBySuccess(products);
		} catch (Exception e) {
			e.printStackTrace();
			serverResponse = ServerResponse.createDefaultErrorMessage(e.getMessage());
		}
		
		
		logger.info("Exit method getProduct params:" + serverResponse);
		return serverResponse;
	}
	
}