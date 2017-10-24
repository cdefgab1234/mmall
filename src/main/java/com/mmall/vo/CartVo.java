package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */
public class CartVo {

    //productVo的一个集合
    private List<CartProductVo> cartProductVos;
    //购物车总价
    private BigDecimal cartTotalPrice;
    //是否已经都勾选
    private boolean allChecked;
    //需要一个主图链接
    private String imageHost;

    public List<CartProductVo> getCartProductVos() {
        return cartProductVos;
    }

    public void setCartProductVos(List<CartProductVo> cartProductVos) {
        this.cartProductVos = cartProductVos;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
