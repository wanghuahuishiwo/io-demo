package com.netease.socket.onethread;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wanghuahui on 2015/7/1.
 */
public class Client {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", 9999);

            OutputStream output = client.getOutputStream();
            byte[] b = new byte[128];
            b[0] = 1;
            b[1] = 2;
            output.write(b);
            System.out.println("==============>");
            byte[] readByte = new byte[128];
            InputStream input = client.getInputStream();
            input.read(readByte);
            int index = 0;
            for(byte rb : readByte) {
                if(rb != 0) {
                    System.out.println("client:"+rb + ", index:" + index);
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
