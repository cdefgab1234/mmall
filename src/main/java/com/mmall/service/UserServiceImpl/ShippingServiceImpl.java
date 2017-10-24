package com.mmall.service.UserServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/22.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        //mybatis useGeneratedKeys="true" 当useGeneratedKeys=true时执行完语句后会把ID返回给添加的对象
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount>0){
            Map result = Maps.newHashMap();
            //所以这里可以拿到id，不用再访问一次数据库
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功",result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败！");
    }

    public ServerResponse<String> del(Integer userId,Integer shippingId){
        //避免一个正常用户，知道这个接口后进行违规操作
        int rowCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
        if (rowCount>0){
            return ServerResponse.createBySuccess("删除地址成功！");
        }
        return ServerResponse.createByErrorMessage("删除地址失败！");
    }

    public ServerResponse<String> update(Integer userId,Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount>0){
            return ServerResponse.createBySuccess("更新地址成功！");
        }
        return ServerResponse.createByErrorMessage("更新地址失败！");
    }

    public ServerResponse<Shipping> select(Integer userId,Integer shippingId){
        Shipping shipping = shippingMapper.selectByUserIdShippingId(userId,shippingId);
        if (shipping==null){
            return  ServerResponse.createByErrorMessage("无法查询到该地址！");
        }
        return ServerResponse.createBySuccess("更新地址成功！",shipping);
    }

    public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }



}
