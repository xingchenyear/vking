package com.vking.controller.backend;

import com.vking.common.Const;
import com.vking.common.ResponseCode;
import com.vking.common.ServerResponse;
import com.vking.pojo.Product;
import com.vking.pojo.User;
import com.vking.service.IProductService;
import com.vking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by XC on 2017/12/28.
 * 后台_产品接口
 */

@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    IUserService iUserService;

    @Autowired
    IProductService iProductService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
       if (iUserService.checkAdmin(user).isSuccess()){
        //增减产品逻辑
           return iProductService.saveOrUpdateProduct(product);
       }else {
           return ServerResponse.createByErrorMessage("无权限操作");
       }

    }

    @RequestMapping("setSaleStatus.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId,Integer status){
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if (iUserService.checkAdmin(user).isSuccess()){
            //逻辑
            return iProductService.setSaleStstus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }

    }
}
