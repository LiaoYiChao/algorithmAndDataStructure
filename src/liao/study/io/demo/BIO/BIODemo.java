package liao.study.io.demo.BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @description: 阻塞IO 和 非阻塞IO
 *
 *  https://blog.csdn.net/qq_34638435/article/details/81878301
 *
 *  阻塞IO 是什么：
 *      用户线程发出IO请求，内核会去查看数据是否就绪，如果没有就绪就会等待数据就绪，
 *      而用户线程就会处于阻塞状态，用户线程交出CPU，当数据就绪后，内核会将数据拷贝
 *      到用户线程，并返回结果给用户线程，用户线程才会解除Block状态。
 *
 *  非阻塞IO 是什么：
 *      用户线程发起读写请求，并不需要等待，而是马上就得到一个结果，如果结果是一个error，
 *      它会知道数据没准备好，于是它再次发送读写请求，一旦内核中的数据准备好了，并且又再次收到了
 *      用户线程的请求，那么它马上就会将数据拷贝到用户线程，然后返回。
 *
 * @author: Liao
 * @date  2020/4/13 18:51
 */
public class BIODemo {

    public static void main(String[] args) {

        /**
         * BIO 代码Demo 案例
         */

        //创建一个线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,0, TimeUnit.MINUTES,
                                                                        new ArrayBlockingQueue<>(10));

        System.out.println("服务端启动");

        try {
            //创建一个ServerSocket 监听端口3080
            ServerSocket serverSocket = new ServerSocket(3080);

            while (true) {
                //一直阻塞等待线程进入
                final Socket socket = serverSocket.accept();

                //有线程 线程池启动，执行其中的方法
                threadPoolExecutor.execute(() -> {
                    MyRunnable(socket);
                });

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void MyRunnable(Socket socket) {
        try {
            byte[] bytes = new byte[1024];

            //获取客户端的 InputStream 流
            InputStream inputStream = socket.getInputStream();

            while (true) {
                //读取其中的数据到 byte 字节中
                int read = inputStream.read(bytes);

                if (read != -1) {
                    System.out.println("当前客户端的输入：" + new String(bytes, 0, read));
                } else {
                    System.out.println("read完成");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
