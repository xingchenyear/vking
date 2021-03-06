package com.vking.dao;

import com.vking.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByProductIdUserId(@Param("productId") Integer productId,@Param("userId") Integer userId);

    List<Cart> selectCartByUserId(Integer userId);

    int selectAllCheckedStatus(Integer userId);

    int deleteByUserIdProductId(@Param("userId") Integer userId,@Param("productList")List<String> productList);

}