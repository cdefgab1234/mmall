package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public interface ICategroyService {

    ServerResponse addCategroy(String categroyName, Integer parentId);

    ServerResponse updateCategroyName(Integer categroyId,String categroyName);

    ServerResponse<List<Category>> getChildrenParallelCategroy(Integer categroyId);

    ServerResponse selectCategroyAndChildrenById(Integer categroyId);
}
