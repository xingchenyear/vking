package com.vking.service.impl;

import com.vking.common.Const;
import com.vking.common.ServerResponse;
import com.vking.common.TokenCache;
import com.vking.dao.UserMapper;
import com.vking.pojo.User;
import com.vking.service.IUserService;
import com.vking.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by XC on 2017/12/17.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //todo 密码登录MD5
        String md5password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);

    }

    @Override
    public ServerResponse<String> register(User user) {
        //校验用户名是否存在

        ServerResponse validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        //设置为普通用户
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("Email已存在");
                }
            }
        } else {
            ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> forgetGetQuestion(String username) {
        ServerResponse checkValid=this.checkValid(username,Const.USERNAME);
        if(checkValid.isSuccess()){
            return ServerResponse.createByErrorMessage("用户名为空");
        }

        String question = userMapper.selectQuestion(username);
        if (!StringUtils.isBlank(question)){
            return ServerResponse.createByErrorMessage("用户未设置密码找回问题");
        }
        return ServerResponse.createBySuccess(question);
    }

    public  ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.selectAnswer(username,question,answer);

        if(resultCount > 0){
            //说明问题及问题答案是正确的
            String token = UUID.randomUUID().toString();
            TokenCache.setKey("token_"+username,token);
            return ServerResponse.createBySuccess(token);
        }
        return ServerResponse.createByErrorMessage("答案错误!");
    }

}
