package com.vking.dao;

import com.vking.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;
// 20180108
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();


    List<Product> selectByNameAndProductId(@Param("productName")String productName, @Param("productId")Integer productId);

    List<Product> selectByNameAndProductId(@Param("productName")String productName,@Param("productId")Integer productId);

}