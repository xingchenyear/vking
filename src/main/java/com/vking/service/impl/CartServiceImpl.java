package com.vking.service.impl;


import com.vking.common.ServerResponse;
import com.vking.dao.CartMapper;
import com.vking.pojo.Cart;
import com.vking.pojo.User;
import com.vking.service.ICartService;
import com.vking.vo.CartProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("iCartService")
public class CartServiceImpl implements ICartService{

    @Autowired
    CartMapper cartMapper;

    public ServerResponse add(Integer userId,Integer productId,int count){
        Cart cart = cartMapper.selectCartByProductIdUserId(userId,productId);
        if (cart == null){
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(count);
            cartMapper.insertSelective(cart);
        }else{
            count = cart.getQuantity()+count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return null;
    }


    private CartProductVo assembleCartListVo(User user){
        return null;

    }


}
