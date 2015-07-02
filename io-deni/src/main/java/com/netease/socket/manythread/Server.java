package com.netease.socket.manythread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wanghuahui on 2015/7/2.
 */
public class Server {


    public static void main(String[] args) {
        try {
            ExecutorService pool = Executors. newFixedThreadPool(100);
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("==================>serverSocket start.");
            int index = 1;
            while (true) {
                Socket socket = serverSocket.accept();
                //new Thread(new Worker(socket)).start();
                pool.execute(new Worker(socket));
                System.out.println("==================>socket:" + index);
                index ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
