package com.vking.dao;

import com.vking.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    //检查的登录名是否存在
    int checkUsername(String username);
    //检查登录名密码
    User selectLogin(@Param("username") String username,@Param("password") String password);
    //检查邮箱是否存在
    int checkEmail(String email);

    String selectQuestion(String username);

    int selectAnswer(@Param("username") String username,@Param("question")String question,@Param("answer")String answer);
}