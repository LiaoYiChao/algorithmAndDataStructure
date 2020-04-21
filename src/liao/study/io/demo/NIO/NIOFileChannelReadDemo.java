package liao.study.io.demo.NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
 * @description: 文件 --> 通道 --> 缓冲区
 * @author: Liao
 * @date  2020/4/19 12:26
 */
public class NIOFileChannelReadDemo {

    public static void main(String[] args) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File("./src/liao/study/io/demo/NIO/test.txt"));

            //获取通道
            FileChannel channel = fileInputStream.getChannel();

            //创建缓冲区
            ByteBuffer allocate = ByteBuffer.allocate(1024);

            //读取数据并写入到缓冲区中
            channel.read(allocate);

            System.out.println(new String(allocate.array()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
