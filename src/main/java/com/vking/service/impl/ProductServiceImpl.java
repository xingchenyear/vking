package com.vking.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.vking.common.ResponseCode;
import com.vking.common.ServerResponse;
import com.vking.dao.CategoryMapper;
import com.vking.dao.ProductMapper;
import com.vking.pojo.Category;
import com.vking.pojo.Product;
import com.vking.service.IProductService;
import com.vking.util.DateTimeUtil;
import com.vking.util.PropertiesUtil;
import com.vking.vo.ProductDetailVO;
import com.vking.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 产品service
 * Created by XC on 2017/12/28.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

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

    public ServerResponse<ProductDetailVO> manageProductDetail(Integer productId){
        if(productId == null){
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);
        return ServerResponse.createBySuccess(productDetailVO);

    }
    private ProductDetailVO assembleProductDetailVO(Product product){
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setId(product.getId());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setSubimage(product.getSubImages());
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setName(product.getName());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","ftp.server.http.prefix"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null){
            productDetailVO.setCategoryId(0);
        }else {
            productDetailVO.setCategoryId(category.getParentId());
        }
        //updateTime
        //createTime
        productDetailVO.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVO.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVO;
    }
    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        //startpage--start
        //填充自己的sql查询逻辑
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }
    private ProductListVo assembleProductListVo(Product product){

        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","ftp.server.http.prefix"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setName(product.getName());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setSubtitle(product.getSubtitle());
        return productListVo;
    }
    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList) {
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }
    public ServerResponse upload(MultipartFile file, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");

        return null;
    }

}
