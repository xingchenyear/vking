package com.vking.service;

import com.vking.common.ServerResponse;
import com.vking.pojo.Product;

/**
 * Created by Administrator on 2017/12/28.
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStstus(Integer productId,Integer status);

}
