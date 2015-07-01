package com.netease.test;

/**
 * Created by wanghuahui on 2015/7/1.
 */
public class TestDemo {

    public static void main(String[] args) {
        byte[] b = new byte[128];
        b[0] = 58;
        b[1] = 33;
        System.out.println(new String(b));


    }
}
