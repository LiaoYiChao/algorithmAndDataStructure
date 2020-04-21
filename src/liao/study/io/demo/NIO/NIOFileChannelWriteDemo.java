package liao.study.io.demo.NIO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
 * @description: NIO - FileChannel 案例
 * @author: Liao
 * @date  2020/4/19 11:19
 */
public class NIOFileChannelWriteDemo {

    public static void main(String[] args) {

        String str = "Hello FileChannel";

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream("./src/liao/study/io/demo/NIO/test2.txt");

            //获取通道
            FileChannel channel = outputStream.getChannel();

            //创建一个缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //将字符串写入到缓冲区
            buffer.put(str.getBytes());

            //反转 即切换成读模式
            buffer.flip();
            channel.write(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
