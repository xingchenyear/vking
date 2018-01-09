package com.vking.service.impl;


import com.google.common.collect.Lists;
import com.vking.common.ServerResponse;
import com.vking.dao.CartMapper;
import com.vking.pojo.Cart;
import com.vking.pojo.User;
import com.vking.service.ICartService;
import com.vking.vo.CartProductVo;
import com.vking.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service("iCartService")
public class CartServiceImpl implements ICartService{

    @Autowired
    CartMapper cartMapper;

    public ServerResponse add(Integer userId,Integer productId,int count){
        Cart cart = cartMapper.selectCartByProductIdUserId(userId,productId);
        if (cart == null){
            Cart cartItem=new Cart();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(count);
            cartMapper.insertSelective(cart);
        }else{
            count = cart.getQuantity()+count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return null;
    }


    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList=Lists.newArrayList();
        List<Cart> cart = cartMapper.selectCartByUserId(userId);
        BigDecimal productTotalPrice = new BigDecimal("0");

        return cartVo;

    }


}
