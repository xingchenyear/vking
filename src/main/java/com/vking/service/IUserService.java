package com.vking.service;

import com.vking.common.ServerResponse;
import com.vking.pojo.User;


/**
 * Created by XC on 2017/12/17.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String string,String type);

    ServerResponse<String> forgetGetQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String question,String answer);
}
