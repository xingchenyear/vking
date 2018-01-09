package com.vking.controller.portal;

import com.vking.common.Const;
import com.vking.common.ResponseCode;
import com.vking.common.ServerResponse;
import com.vking.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart/")
public class CartController {


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getCartList(HttpSession session,Integer productId,Integer userId,int count){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

        return null;
    }

}
