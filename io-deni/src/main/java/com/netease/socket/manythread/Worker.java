package com.netease.socket.manythread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wanghuahui on 2015/7/2.
 */
public class Worker implements  Runnable {

    private Socket socket = null;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Long start = System.currentTimeMillis();
        InputStream input = null;
        OutputStream outPut = null;
        try{

            input = socket.getInputStream();
            byte[] rb = new byte[1024];
            input.read(rb);

            for(byte v : rb) {
                if(v == 0) {
                    break;
                }
                // System.out.println("server:" + v);
            }
            Thread.sleep(100);
            rb[2] = 2;
            rb[3] = 3;
            outPut = socket.getOutputStream();
            outPut.write(rb);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                outPut.flush();
                outPut.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Long end = System.currentTimeMillis();

        System.out.println("cost: " + (end - start));
    }
}
