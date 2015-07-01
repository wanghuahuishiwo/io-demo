package com.netease.socket;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wanghuahui on 2015/7/1.
 */
public class Server {


    public static void main(String[] args) {
        try {
            System.out.println("--------server----------");
            ServerSocket server = new ServerSocket(9999);
            Socket socket =  server.accept();

            InputStream input = socket.getInputStream();
            byte[] b = new byte[1024];
            input.read(b);
            for(byte v : b) {
                if(v == 0) {
                    break;
                }
                System.out.println("server:" + v);
            }

            OutputStream output = socket.getOutputStream();
            b[2] = 2;
            b[3] = 3;
            b[4] = 4;
            output.write(b);

            input.close();
            output.flush();
            output.close();
            socket.close();
            //server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
