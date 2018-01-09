package com.vking.controller.portal;

import com.vking.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cart/")
public class CartController {


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getCartList(){
        return  null;
    }

}
