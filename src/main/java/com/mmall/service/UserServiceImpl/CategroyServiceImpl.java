package com.mmall.service.UserServiceImpl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategroyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/10.
 */
@Service("iCategroyService")
public class CategroyServiceImpl implements ICategroyService {

    private Logger logger = LoggerFactory.getLogger(CategroyServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategroy(String categroyName,Integer parentId){
        if (parentId==null || StringUtils.isBlank(categroyName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误!");
        }
        Category category = new Category();
        category.setName(categroyName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount>0){
            return ServerResponse.createBySuccessMessage("添加品类成功!");
        }
        return ServerResponse.createByErrorMessage("添加品类失败!");
    }

    public ServerResponse updateCategroyName(Integer categroyId,String categroyName){
        if (categroyId==null || StringUtils.isBlank(categroyName)){
            return ServerResponse.createByErrorMessage("更新品类参数错误!");
        }
        Category category = new Category();
        category.setId(categroyId);
        category.setName(categroyName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount>0){
            return ServerResponse.createBySuccessMessage("更新品类名字成功!");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败!");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategroy(Integer categroyId){
        List<Category> categoryList = categoryMapper.selectCategroyChildrenByParentId(categroyId);
        if (CollectionUtils.isEmpty(categoryList)){
           logger.info("未找到当前分类的子分类!");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查找本节点的id和孩子节点的id
     * @param categroyId
     * @return
     */
    public ServerResponse selectCategroyAndChildrenById(Integer categroyId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildrenCategroy(categorySet,categroyId);

        List<Integer> categroyList = Lists.newArrayList();
        if (categroyId!=null){
            for (Category category:categorySet){
                categroyList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(categroyList);
    }

    //递归函数=>递归算法算出子节点=>自己调自己
    public Set<Category> findChildrenCategroy (Set<Category> categorySet,Integer categroyId){
        Category category = categoryMapper.selectByPrimaryKey(categroyId);
        if (category!=null){
            categorySet.add(category);
        }
        //查找子节点，递归算法一定要有一个退出条件
        //当前业务中，如果子节点是空的就退出递归循环
        List<Category> categoryList = categoryMapper.selectCategroyChildrenByParentId(categroyId);
        //这里一般要进行一个空判断，由于mybatis没有查到的话不会返回一个空
        for(Category categroy:categoryList){
            findChildrenCategroy(categorySet,categroy.getId());
        }
        return categorySet;
    }

}
