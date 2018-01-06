package com.vking.service.impl;

import com.vking.common.ResponseCode;
import com.vking.common.ServerResponse;
import com.vking.dao.ProductMapper;
import com.vking.pojo.Product;
import com.vking.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品service
 * Created by XC on 2017/12/28.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    public ServerResponse saveOrUpdateProduct(Product product){
        if(product != null){
            if(StringUtils.isNotBlank(product.getMainImage())){
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                    return ServerResponse.createByErrorMessage("更新失败");
            }else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0){
                    return ServerResponse.createBySuccess("新增商品成功");
                }
                return ServerResponse.createByErrorMessage("新增产品失败");

            }
        }
        return ServerResponse.createByErrorMessage("新增产品参数不正确");
    }

    public ServerResponse<String> setSaleStstus(Integer productId,Integer status){
        if (productId ==null || status == null ){
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0){
            return ServerResponse.createBySuccess("修改商品状态成功");
        }
        return ServerResponse.createByErrorMessage("修改商品状态失败");
    }

}
