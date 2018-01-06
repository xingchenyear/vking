package com.vking.controller.backend;

import com.vking.common.Const;
import com.vking.common.ResponseCode;
import com.vking.common.ServerResponse;
import com.vking.pojo.User;
import com.vking.service.ICategoryService;
import com.vking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 产品分类
 * Created by XC on 2017/12/23.
 */

@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请登录");
        }
        //校验是否是管理员
        if(iUserService.checkAdmin(user).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            return iCategoryService.addCatrgory(categoryName,parentId);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作需要管理员权限");
        }
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
     public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
         User user = (User)session.getAttribute(Const.CURRENT_USER);
         if(user == null){
             return ServerResponse.cteateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请登录");
         }

         if(iUserService.checkAdmin(user).isSuccess()){
             //更新categoryName
             return iCategoryService.updateCategoryName(categoryId,categoryName);
         }else{
             return ServerResponse.createByErrorMessage("无权限操作需要管理员权限");
         }
     }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请登录");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            // 查询子节点的categoryId不递归保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.cteateByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请登录");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            // 查询当前节点的ID和递归子节点的id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作需要管理员权限");
        }
    }



}
