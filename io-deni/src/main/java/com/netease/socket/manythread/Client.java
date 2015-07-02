package com.netease.socket.manythread;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wanghuahui on 2015/7/1.
 */
public class Client {

    public static void main(String[] args) {
        int index = 10;
        final CountDownLatch lath = new CountDownLatch(index);

        Long start = System.currentTimeMillis();
        for(int i = 0; i<index; i++) {
            new Thread(new Runnable() {
                public void run() {
                    toServer();
                    lath.countDown();
                }
            }).start();
        }
        try {
            lath.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("=========================>");
        System.out.println(index + ", cost:" + (end - start));
        //50, cost:36
        //50, cost:29
        //50, cost:32
        //100, cost:65
        //100, cost:50
        //100, cost:46
        //100, cost:56
    }

    public static void toServer() {
        try {
            Socket client = new Socket("127.0.0.1", 9999);

            OutputStream output = client.getOutputStream();
            byte[] b = new byte[128];
            b[0] = 1;
            b[1] = 2;
            output.write(b);
            //System.out.println("==============>");
            byte[] readByte = new byte[128];
            InputStream input = client.getInputStream();
            input.read(readByte);
            int index = 0;
            for(byte rb : readByte) {
                if(rb != 0) {
                    //System.out.println("client:"+rb + ", index:" + index);
                }
                index ++;
            }

            input.close();
            output.flush();
            output.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
