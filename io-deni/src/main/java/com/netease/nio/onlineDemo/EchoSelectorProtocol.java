package com.netease.nio.onlineDemo;

import com.netease.nio.model.dto.UserDto;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wanghuahui on 2015/7/7.
 */
public class EchoSelectorProtocol implements  TCPProtocol {
    private int bufSize;

    public EchoSelectorProtocol(int bufSize){
        this.bufSize =bufSize;
    }

    @Override
    public void handleAccept(SelectionKey key,int  index) throws IOException {
        System.out.println("handleAccept start:" + index);
        SocketChannel clntChan = ((ServerSocketChannel)key.channel()).accept();
        clntChan.configureBlocking(false);
        //对于已注册Selector的Channel,再次调用就更新注册信息,这里更新了SelectionKey的类型和附件,附件是需要操作的buffer.
        clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
        System.out.println("handleAccept end:" + index);
    }

    @Override
    public void handleRead(SelectionKey key,int  index) throws IOException {
        System.out.println("handleRead start:" + index);
        SocketChannel clntChan =(SocketChannel)key.channel();
        ByteBuffer buf =(ByteBuffer) key.attachment(); //获取附件
        long bytesRead = clntChan.read(buf);
        //buf.flip();
        System.out.println("handleRead data:" + new String(buf.array(), 0, buf.limit()));
        System.out.println("bytesRead:" + bytesRead);
        if(bytesRead==-1){//channel已读到结束位置
            //clntChan.close();
        }
        else if(bytesRead > 0 && bytesRead<1657) {
            key.interestOps(SelectionKey.OP_READ);
            System.out.println("handleRead interestOps OP_READ:" + index);
        }
        else if(bytesRead == 1657) {
            key.interestOps(SelectionKey.OP_WRITE);
            System.out.println("handleRead interestOps OP_WRITE:" + index);
        }
        System.out.println("handleRead end:" + index);
    }

    @Override
    public void handleWrite(SelectionKey key,int  index) throws IOException {
        System.out.println("handleWrite write start:" + index);
        ByteBuffer buf =(ByteBuffer) key.attachment();
        System.out.println("handleWrite:" + buf );
        buf.flip();
        System.out.println("handleWrite:" + new String(buf.array(),0,buf.limit()));
        SocketChannel clntChan =(SocketChannel) key.channel();
        int writeLen = clntChan.write(ByteBuffer.wrap(getUserDto().toString().getBytes()));
        System.out.println("writeLen:" + writeLen);
        key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
//        if(!buf.hasRemaining()){
//            key.interestOps(SelectionKey.OP_READ); //设置Key的兴趣集
//        }


        buf.compact();
        System.out.println("handleWrite write end:" + index);

    }


    public static UserDto getUserDto() {
        String desc = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
        UserDto user = new UserDto();
        user.setName("wanghuahui");
        user.setAge(26);
        user.setDesc(desc + desc + desc);
        return user;
    }
}
