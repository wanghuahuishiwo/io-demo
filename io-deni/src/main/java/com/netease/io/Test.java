package com.netease.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wanghuahui on 2015/6/30.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        Long start = System.currentTimeMillis();

        int count = 800;
        final CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try{
                        Test.read();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }

                }
            }).start();
        }
        latch.await();

        Long end = System.currentTimeMillis();
        System.out.println("==================>");
        System.out.println("nio cost:" + (end - start));
        System.out.println("<==================");







        Long start1 = System.currentTimeMillis();

        final CountDownLatch latch1 = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try{
                        Test.readIo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch1.countDown();
                    }

                }
            }).start();
        }
        latch1.await();

        Long end1 = System.currentTimeMillis();
        System.out.println();
        System.out.println("io cost:" + (end1 - start1));

    }

    public static void read() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\test.txt");
        FileChannel fc = fis.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(4);

        int hasRead = 0;
        while((hasRead= fc.read(byteBuffer)) > 0) {

            byteBuffer.flip();
            String s = new String(byteBuffer.array());
            // byteBuffer.
            //System.out.print(new String(byteBuffer.array()));
        }


       // System.out.println();
        fc.close();
        fis.close();
    }

    public static void readIo() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\test.txt");
        byte[] b = new byte[4];

        int hasRead = 0;

        while ((hasRead = fis.read(b)) > 0) {
            String s = new String(b, 0, hasRead);
           // System.out.print(s);
        }
        fis.close();
    }

}
