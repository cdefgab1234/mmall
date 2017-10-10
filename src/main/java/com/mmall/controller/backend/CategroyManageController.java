package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategroyService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/10/10.
 */
@Controller
@RequestMapping("/manage/categroy")
public class CategroyManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategroyService iCategroyService;

    //增加分类,添加一个新的分类
    @RequestMapping(value = "add_categroy.do")
    @ResponseBody
    public ServerResponse addCategroy(HttpSession session,String categroyName,
                                      @RequestParam(value = "parentId",defaultValue = "0") int parentId){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录!");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //是管理员
            //增加处理分类的逻辑
            return iCategroyService.addCategroy(categroyName,parentId);

        }else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限！");
        }
    }

    //更新categoryName
    @RequestMapping(value = "set_categroy_name.do")
    @ResponseBody
    public ServerResponse setCategroyName(HttpSession session,Integer categroyId,String categroyName){
        //是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录!");
        }
        //是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //更新categoryName
            return iCategroyService.updateCategroyName(categroyId,categroyName);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限！");
        }
    }

    //根据当前的id获取它的平级子节点
    @RequestMapping(value = "get_categroy.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategroy(HttpSession session,
                                                      @RequestParam(value = "categroyId",defaultValue = "0") Integer categroyId){
        //是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录!");
        }
        //是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //查询子节点的categroy信息，不递归，保持平级
            return iCategroyService.getChildrenParallelCategroy(categroyId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限！");
        }
    }

    //根据当前id查找所有子节点和递归子节点的id
    @RequestMapping(value = "get_deep_categroy.do")
    @ResponseBody
    public ServerResponse getCategroyAndDeepChildrenCategroy(HttpSession session,
                                                      @RequestParam(value = "categroyId",defaultValue = "0") Integer categroyId){
        //是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录!");
        }
        //是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()){
            //查询当前节点的id和递归子节点的id
            return iCategroyService.selectCategroyAndChildrenById(categroyId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限！");
        }
    }

}
