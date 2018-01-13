package com.vking.service;

import com.vking.common.ServerResponse;
import com.vking.vo.CartVo;

public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    ServerResponse<CartVo> delete(Integer userId,String productId);

}
