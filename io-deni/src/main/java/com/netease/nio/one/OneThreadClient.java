package com.netease.nio.one;

import com.netease.nio.model.dto.UserDto;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2015/7/4
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */
public class OneThreadClient {


    public static void main(String[] args) {
        UserDto user = OneThreadClient.getUserDto();

        SocketChannel channel = null;
        try {

            channel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
            channel.configureBlocking(false);
            int write = channel.write(ByteBuffer.wrap(user.toString().getBytes()));
            System.out.println("write:" + write);

            Thread.sleep(1000);
            ByteBuffer bb = ByteBuffer.allocate(128);
            int flag = channel.read(bb);
            while(flag > 0) {

                bb.flip();
                int limit = bb.limit();

                System.out.println(new String(bb.array(), 0 , limit));

//                System.out.println("limit:" + bb.limit());
//                System.out.println("capacity:" + bb.capacity());
//                System.out.println("mark:" + bb.mark());
//                System.out.println("position:" + bb.position());

//                bb.clear();
//                System.out.println("clear:" + new String(bb.array()));
                flag = channel.read(bb);
                bb.clear();
            }

            System.out.println("client end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("client close.");
        }


    }













































    public static UserDto getUserDto() {
        String desc = "sadeasdadasdwqrfksal;dfksad;lfksadl;fks;ldafk" +
                "l;sdkfls;aoipweripoieqweqweqweqweqweqeqewqeq" +
                "sadeasdadasdwqrfksal;dfksad;lfksadl;fks;ldafkl;sdkfls;aoipweripoieqweqweqweqweqweqeqewqeq" +
                "sadeasdadasdwqrfksal;dfksad;lfksadl;fks;ldafkl;sdkfls;aoipweripoieqweqweqweqweqweqeqewqeq" +
                "sadeasdadasdwqrfksal;dfksad;lfksadl;fks;ldafkl;sdkfls;aoipweripoieqweqweqweqweqweqeqewqeq" +
                "sadeasdadasdwqrfksal;dfksad;lfksadl;fks;ldafkl;sdkfls;aoipweripoieqweqweqweqweqweqeqewqeq" +
                "sadeasdadasdwqrfksal;dfksad;lfksadl;fks;ldafkl;sdkfls;aoipweripoieqweqweqweqweqweqeqewqeq";
        UserDto user = new UserDto();
        user.setName("wanghuahui");
        user.setAge(26);
        user.setDesc(desc + desc + desc);
        return user;
    }
}
