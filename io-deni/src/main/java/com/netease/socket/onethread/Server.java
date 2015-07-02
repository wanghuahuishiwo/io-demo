package com.netease.socket.onethread;

import com.netease.socket.manythread.Worker;
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
            int index = 1;
            while(true) {

                Socket socket =  server.accept();
                new Worker(socket).run();
                System.out.println("==================>socket:" + index);
                index++;
//                InputStream input = socket.getInputStream();
//                byte[] b = new byte[1024];
//                input.read(b);
//                for(byte v : b) {
//                    if(v == 0) {
//                        break;
//                    }
//                   // System.out.println("server:" + v);
//                }
//                System.out.println("server:" + index);
//                OutputStream output = socket.getOutputStream();
//                b[2] = 2;
//                b[3] = 3;
//                b[4] = 4;
//                output.write(b);
//
//                input.close();
//                output.flush();
//                output.close();
//                socket.close();

            }

            //server.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
