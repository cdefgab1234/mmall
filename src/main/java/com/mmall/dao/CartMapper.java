package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    //根据用户id和产品id查找购物车
    Cart selectCartByUserIdProductId(@Param("userId") Integer userId,@Param("productId")  Integer productId);

    //根据id查找购物车中的产品
    List<Cart> selectCartByUserId (Integer userId);

    //查购物车产品未被选中的个数
    int selectCartProductCheckedStatusByUserId(Integer userId);

    int deleteByUserIdProductIds(@Param("userId") Integer userId,@Param("productList") List<String> productList);

    int checkedOrUncheckedProductId (@Param("userId") Integer userId,@Param("productId")  Integer productId,@Param("checked") Integer checked);

    int selectCartProductCount(@Param("userId") Integer userId);
}