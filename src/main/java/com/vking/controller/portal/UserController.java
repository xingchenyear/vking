package com.vking.controller.portal;

import com.vking.common.Const;
import com.vking.common.ResponseCode;
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
 * Created by XC on 2017/12/17.
 * Controller
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * @param username 用户名
     * @param password 密码
     * @param session  session
     * @return ServerResponse 接口数据
     */
    @ResponseBody
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @ResponseBody
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    @ResponseBody
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @ResponseBody
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取用户信息");
    }

    @ResponseBody
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.forgetGetQuestion(username);
    }

    @ResponseBody
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    @ResponseBody
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @ResponseBody
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(user, passwordOld, passwordNew);
    }

    @ResponseBody
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    public ServerResponse<User> update_information(HttpSession session, User user) {
        User sessionUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (sessionUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(sessionUser.getId());
        user.setUsername(sessionUser.getUsername());
        ServerResponse<User> response = iUserService.update_information(user);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    public ServerResponse<User> get_information(HttpSession session){
        User sessionUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (sessionUser == null) {
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录需要强制登录");
        }
        return iUserService.getInformation(sessionUser.getId());
    }





}
