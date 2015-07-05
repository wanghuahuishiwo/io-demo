package com.netease.nio.all;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2015/7/5
 * Time: 9:59
 * To change this template use File | Settings | File Templates.
 */
public class AllThreadServer {



    public static void main(String[] args) {
        try {
            final Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(9999));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            selector.select();
                            Set<SelectionKey> keys = selector.selectedKeys();
                            Iterator<SelectionKey> itr = keys.iterator();
                            while (itr.hasNext()) {
                                SelectionKey key = itr.next();
                                if(key.isAcceptable()) {
                                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                                    server.accept();

                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }).start();




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
