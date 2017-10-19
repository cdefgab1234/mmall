package com.mmall.vo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/10/12.
 */
public class ProductDetailVo {

    private Integer id;
    private Integer categroyId;
    private Integer parentCategroyId;
    private String name;
    private String subtitle;
    private String mainImages;
    private String subImages;
    private String detail;
    private BigDecimal pricel;
    private Integer stock;
    private Integer status;
    private String createtime;
    private String updatetime;
    private String imageHost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategroyId() {
        return categroyId;
    }

    public void setCategroyId(Integer categroyId) {
        this.categroyId = categroyId;
    }

    public Integer getParentCategroyId() {
        return parentCategroyId;
    }

    public void setParentCategroyId(Integer parentCategroyId) {
        this.parentCategroyId = parentCategroyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImages() {
        return mainImages;
    }

    public void setMainImages(String mainImages) {
        this.mainImages = mainImages;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPricel() {
        return pricel;
    }

    public void setPricel(BigDecimal pricel) {
        this.pricel = pricel;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
