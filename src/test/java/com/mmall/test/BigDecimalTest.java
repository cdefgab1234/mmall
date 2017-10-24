package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/10/20.
 */
public class BigDecimalTest {

    //和想象不太一样，很容易丢失精度
    //很多语言都有专门的货币计算，java没有
    //java在浮点型运算过程中会丢失精度
    //很可能造成一个用户有10元钱不能购买9.9999元的商品
    @Test
    public void test1(){
        System.out.println(0.05+0.01);
        System.out.println(1.0+0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
        /*
        0.060000000000000005
        1.42
        401.49999999999994
        1.2329999999999999
         */

    }

    @Test
    public void test2(){
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        //b1.add(b2);
        System.out.println(b1.add(b2));
        //0.06000000000000000298372437868010820238851010799407958984375
        //又长又乱
    }

    @Test
    public void test3(){
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
        //0.06
        //用string 构造器，完美解决
    }

}
