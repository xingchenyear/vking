package com.vking.service;

import com.github.pagehelper.PageInfo;
import com.vking.common.ServerResponse;
import com.vking.pojo.Product;
import com.vking.vo.ProductDetailVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by XC on 2017/12/28.
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVO> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    ServerResponse upload(MultipartFile file, HttpServletRequest request);

}
