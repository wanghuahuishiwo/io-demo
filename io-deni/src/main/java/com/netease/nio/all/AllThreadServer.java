package com.netease.nio.all;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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

            System.out.println("server start.");

            while (true) {
                try {
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> itr = keys.iterator();
                    while (itr.hasNext()) {
                        final SelectionKey key = itr.next();
                        itr.remove();

                        Long id =  Thread.currentThread().getId();
                        try {
                            if(key.isAcceptable()) {
                                System.out.println("id:" + id + ", isAcceptable");
                                ServerSocketChannel server = (ServerSocketChannel)key.channel();
                                SocketChannel sockteChannel = server.accept();
                                sockteChannel.configureBlocking(false);
                                sockteChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2048));
                            }
                            else if(key.isReadable()) {
                                System.out.println("id:" + id + ", read");
                                SocketChannel socketChannel = (SocketChannel)key.channel();
                                ByteBuffer readBuffer = (ByteBuffer)key.attachment();
                                int count = socketChannel.read(readBuffer);
                                if(count > 0) {
                                    String s = new String(readBuffer.array(),0,readBuffer.limit());
                                    System.out.println("id:" + id + ", " + s);
                                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                                    //key.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                                }
                                else {
                                    System.out.println("id:" + id + ", read none datas.");
                                    socketChannel.close();
                                }
                                System.out.println("id:" + id + ", read finish.");
                            }
                            else if(key.isWritable()) {
                                System.out.println("id:" + id + ", write");
                                SocketChannel sockteChannel = (SocketChannel)key.channel();
                                //sockteChannel.register(selector, SelectionKey.OP_READ);
                                ByteBuffer s = (ByteBuffer) key.attachment();
                                System.out.println("id:" + id + ", att:" + new String(s.array()));
                                s.flip();
                                sockteChannel.write(s);
                                System.out.println("id:" + id + ", write finish.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
