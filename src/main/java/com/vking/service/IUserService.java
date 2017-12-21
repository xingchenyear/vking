package com.vking.service;

import com.vking.common.ServerResponse;
import com.vking.pojo.User;

import javax.servlet.http.HttpSession;


/**
 * Created by XC on 2017/12/17.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String string,String type);

    ServerResponse<String> forgetGetQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);

    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew);

    ServerResponse<User> update_information(User user);

    ServerResponse<User> getInformation(Integer userId);
}
