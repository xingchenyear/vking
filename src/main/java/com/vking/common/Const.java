package com.vking.common;

/**
 * Created by XC on 2017/12/17.
 * 常量类
 */
public class Const {
    //
    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role {
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;  //管理员
    }

    public interface Cart{
        int CHECKED = 1; //选中状态
        int UN_CHECKER = 0;// 未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUMFAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }


}
