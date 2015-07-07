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



        Selector selector =Selector.open(); //����Selectorʵ��
        ServerSocketChannel listnChannel =ServerSocketChannel.open();
        listnChannel.socket().bind(new InetSocketAddress(9999));
        listnChannel.configureBlocking(false); //nonblocking
        listnChannel.register(selector, SelectionKey.OP_ACCEPT); //Channelע��selector,����֪channel����Ȥ�Ĳ���

        TCPProtocol protocol =new EchoSelectorProtocol(BUFSIZE);

        System.out.println("server start.");
        int index = 0;
        while(true){ //ѭ��
            if(selector.select(TIMEOUT)==0){ //����׼������I/O��SelectionKey��
                continue;
            }

            Iterator<SelectionKey> keyIter =selector.selectedKeys().iterator();//��ȡ��ѡ��SelectionKey����
            System.out.println("index:" + index + ", selectKey size:" + selector.selectedKeys().size());
            while(keyIter.hasNext()){ //����key,����key����������Ӧ����
                SelectionKey key =keyIter.next();
                keyIter.remove(); //�ֶ����,��ΪSelectorֻ������ѡ��SelectionKey�������
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
