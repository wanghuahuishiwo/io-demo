package com.netease.nio.onlineDemo;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by wanghuahui on 2015/7/7.
 */
public interface TCPProtocol {
    void handleAccept(SelectionKey key,int index) throws IOException;
    void handleRead(SelectionKey key,int  index) throws IOException;
    void handleWrite(SelectionKey key,int  index) throws IOException;

}
