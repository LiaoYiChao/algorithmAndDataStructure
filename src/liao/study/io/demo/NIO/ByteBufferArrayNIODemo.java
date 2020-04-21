package liao.study.io.demo.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.stream.Stream;

/*
 * @description: 数组形式的 Buffer，当一个Buffer 不够使用的时候，使用
 *                  测试案例：很多地方没有完善....
 * @author: Liao
 * @date  2020/4/19 23:54
 */
public class ByteBufferArrayNIODemo {

    public static void main(String[] args) {

        /**
         * 创建一个ByteBuffer 数组
         */
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(10);
        byteBuffers[1] = ByteBuffer.allocate(10);

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            //serverSocketChannel.bind(InetSocketAddress.createUnresolved("127.0.0.1",10301));

            serverSocketChannel.bind(new InetSocketAddress(3333));

            //等待客户端
            SocketChannel accept = serverSocketChannel.accept();

            int maxBufferAyyayLenth = 20;
            while (true) {
                int lenth = 0;

                while (lenth < maxBufferAyyayLenth) {
                    /**
                     * 这里的 read 为传入的字节数量
                     */
                    long read = accept.read(byteBuffers);
                    lenth += read;
                    Arrays.stream(byteBuffers).forEach(byteBuffer -> {
                        System.out.println("position:" + byteBuffer.position() + " - "
                                + "limit:" + byteBuffer.limit());
                    });
                }

                Arrays.stream(byteBuffers).forEach(Buffer::flip);

                long write = accept.write(byteBuffers);
                if (write == -1) {
                    break;
                }

                Arrays.stream(byteBuffers).forEach(Buffer::clear);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
