package com.vking.service.impl;



import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vking.common.ServerResponse;
import com.vking.dao.CategoryMapper;
import com.vking.pojo.Category;
import com.vking.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 分类实现
 * Created by XC on 2017/12/23.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService{

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCatrgory(String categoryName,Integer parentId){
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return  ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//分了用
        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0 ){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("商品类添加失败");

    }

    public ServerResponse updateCategoryName(Integer categoryId,String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return  ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int rowCount=categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount>0){
            return ServerResponse.createBySuccessMessage("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("品类更新名字失败");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询基本节点id 及孩子节点id
     * @param categoryId
     * @return fanhuijidian
     */
    public ServerResponse selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildrenCategory(categorySet,categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null){
            for (Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    //递归算出子节点
    private Set<Category> findChildrenCategory(Set<Category> categorySet ,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        //查找节点要有一个退出条件
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList){
            findChildrenCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
