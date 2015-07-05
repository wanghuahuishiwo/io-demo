package com.netease.nio.one;

import com.netease.nio.model.dto.UserDto;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2015/7/4
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public class OneThreadServer {


    public static void main(String[] args) {
        System.out.println("====================>server");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));
            while (true) {
                SocketChannel channel = serverSocketChannel.accept();
                if(channel != null) {
                    System.out.println("接受新连接");
                    channel.configureBlocking(false);
                    ByteBuffer bb = ByteBuffer.allocate(2048);

                    Thread.sleep(200);

                    int flag = channel.read(bb);
                    System.out.println("flag:" + flag);

                    while(flag > 0) {
                        bb.flip();
                        int limit = bb.limit();
                        System.out.println(new String(bb.array(), 0 , limit));
                        flag = channel.read(bb);
                        System.out.println("server flag:" + flag);
                    }

                    Thread.sleep(200);
                    System.out.println("server write");
                    UserDto user = OneThreadServer.getUserDto();
                    bb.clear();
                    channel.write( ByteBuffer.wrap(user.toString().getBytes()));

                    channel.close();
                    System.out.println("channel close");
                }
            }




            //channel.close();
            //serverSocketChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    public static UserDto getUserDto() {
        UserDto user = new UserDto();
        user.setName("wanghuahui");
        user.setAge(26);
        user.setMoney(12.6d);
        return user;
    }
}
