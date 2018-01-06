package com.vking.service;

import com.vking.common.ServerResponse;
import com.vking.pojo.Category;

import java.util.List;

/**
 * 分类Service
 * Created by XC on 2017/12/23.
 */
public interface ICategoryService {

    ServerResponse addCatrgory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    public ServerResponse selectCategoryAndChildrenById(Integer categoryId);

}
