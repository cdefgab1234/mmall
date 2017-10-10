package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/8.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化接口的时候，如果为null的对象，key也会消失
public class ServerResponse<T> implements Serializable {

    private int states;
    private String msg;
    private T data;

    private ServerResponse(int states) {
        this.states = states;
    }

    private ServerResponse(int states, T data) {
        this.states = states;
        this.data = data;
    }

    private ServerResponse(int states, String msg, T data) {
        this.states = states;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int states, String msg) {
        this.states = states;
        this.msg = msg;
    }

    @JsonIgnore
    //使之不在json序列化接口之中
    public boolean isSuccess(){
        return this.states==ResponseCode.SUCCESS.getCode();
    }

    public int getStates() {
        return states;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    //只要正确的状态
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    //只要正确的说明
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    //只要正确的数据
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    //公共错误，枚举类里面写好了
    public static <T> ServerResponse<T> createByError(){
        return  new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    //返回一个自定义的错误
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    //暴露一个自定义code的方法
    public static <T> ServerResponse<T> createByErrorMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }
}
