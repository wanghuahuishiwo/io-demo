package com.netease.nio.onlineDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * Created by wanghuahui on 2015/7/7.
 */
public class TCPServerSelector {
    private static final int BUFSIZE = 2048;  //Buffer,EchoSelectorProtocol
    private static final int TIMEOUT = 3000; //Selector.select(long timeout)

    public static void main(String[] args) throws IOException {



        Selector selector =Selector.open(); //创建Selector实例
        ServerSocketChannel listnChannel =ServerSocketChannel.open();
        listnChannel.socket().bind(new InetSocketAddress(9999));
        listnChannel.configureBlocking(false); //nonblocking
        listnChannel.register(selector, SelectionKey.OP_ACCEPT); //Channel注册selector,并告知channel感兴趣的操作

        TCPProtocol protocol =new EchoSelectorProtocol(BUFSIZE);

        System.out.println("server start.");
        int index = 0;
        while(true){ //循环
            if(selector.select(TIMEOUT)==0){ //返回准备就绪I/O的SelectionKey数
                continue;
            }

            Iterator<SelectionKey> keyIter =selector.selectedKeys().iterator();//获取已选的SelectionKey集合
            System.out.println("index:" + index + ", selectKey size:" + selector.selectedKeys().size());
            while(keyIter.hasNext()){ //遍历key,根据key的类型做相应处理
                SelectionKey key =keyIter.next();
                keyIter.remove(); //手动清空,因为Selector只会在已选的SelectionKey集中添加
                if(key.isAcceptable())
                    protocol.handleAccept(key, index);

                if(key.isReadable())

                    protocol.handleRead(key, index);

                //SelectionKey is invalid if it is cancelled, its channel is closed, or its selector is closed.
                if(key.isValid() && key.isWritable())
                    protocol.handleWrite(key, index);

            }
            index++;
        }

    }
}
