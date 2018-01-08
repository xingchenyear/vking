package com.vking.controller.backend;

import com.vking.common.Const;
import com.vking.common.ServerResponse;
import com.vking.pojo.User;
import com.vking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by XC on 2017/12/21.
 * 后台登录
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse response = iUserService.login(username,password);
        if (response.isSuccess()){
            User user = (User) response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN){
                //说明是管理员
                session.setAttribute(Const.CURRENT_USER,user);
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登陆");
            }
        }
        return response;
    }
}
