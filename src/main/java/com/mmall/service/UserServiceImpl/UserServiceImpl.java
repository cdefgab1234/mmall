package com.mmall.service.UserServiceImpl;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCatch;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.until.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Administrator on 2017/10/8.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {

        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在！");
        }
        //todo 密码登陆md5
        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username,md5Password);
        if(user==null){
            return ServerResponse.createByErrorMessage("密码错误！");
        }

        //登陆成功后，我将密码置成空，避免将密码传到前台的风险
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登陆成功！",user);
    }

    public ServerResponse<String> register(User user) {
        /*int resultCount = userMapper.checkUsername(user.getUsername());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("用户名已存在！");
        }*/
        /*int resultCount = userMapper.checkEmail(user.getEmail());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("邮箱已被注册！");
        }*/
        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if (!validResponse.isSuccess()){
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功！");
    }

    //简化代码的提出来重复使用的公共类 type可能是username，email根据业务而定
    //检查是否有效
    public ServerResponse<String> checkValid(String str,String type){
        if (StringUtils.isNotBlank(type)){
            //开始校验
            if (Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在！");
                }
            }
            if (Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("邮箱已被注册！");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误！");
        }
        return ServerResponse.createBySuccessMessage("校验成功！");
    }

    public ServerResponse<String> selectQuestion(String username){
        ServerResponse checkValid = this.checkValid(username,Const.USERNAME);
        if (checkValid.isSuccess()){
            //根据上面的方法，当error：1的时候用户名已存在，那么success：0的时候用户名不存在
            //用户不存在
            return ServerResponse.createByErrorMessage("用户名不存在！");
        }
        String Question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(Question)){
            return ServerResponse.createBySuccess(Question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的！");
    }

    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if (resultCount>0){
            //说明输入的用户名，问题和密码都是正确的
            String forgetToken = UUID.randomUUID().toString();
            TokenCatch.setKey(TokenCatch.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误！");
    }

    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，forgetToken需要被传递！");
        }
        //如果参数没有错误，老方法检查一下username的合理性
        ServerResponse checkValid = this.checkValid(username, Const.USERNAME);
        if (checkValid.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户名不存在！");
        }
        String Token = TokenCatch.getKey(TokenCatch.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(Token)) {
            return ServerResponse.createByErrorMessage("Token无效或者过期！");
        }
        if (StringUtils.equals(forgetToken, Token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);

            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功！");
            }
        } else {
            return ServerResponse.createByErrorMessage("请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败！");
    }

    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权，校验这个用户的旧密码一定要指向这个用户
        //不然select count（1）找的话人多了很容易查到一个随便的密码，count(1)>0就通过了
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if (resultCount==0){
            return ServerResponse.createByErrorMessage("旧密码错误！");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount>0){
            return ServerResponse.createBySuccessMessage("密码更新成功！");
        }
        return ServerResponse.createByErrorMessage("密码更新失败！");
    }

    public ServerResponse<User> updateInformation(User user){
        //username不能被更新
        //email也要进行一个校验，如果email已经存在，就不能是当前的email
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if (resultCount>0){
            return ServerResponse.createByErrorMessage("email已经存在，请更换email在尝试更新");
        }
        //写了一大堆就为了把修改信息弄到数据库里
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount>0){
            return ServerResponse.createBySuccess("更新个人用户信息成功！",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人用户信息失败！");
    }

    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if (user==null){
            return ServerResponse.createByErrorMessage("找不到当前用户！");
        }
        //找到当前用户将密码置成空,获取用户详细信息时后台不会将密码传到前端
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }


    //backend

    /**
     * 校验是否为管理员
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        //这里的intvalue注意一下，user实体类里面是integer，integer和int的区别就是前者自带缓存
        if (user!=null && user.getRole().intValue()==Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
