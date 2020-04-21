package liao.study.io.demo.NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.*;
import java.time.temporal.ChronoUnit;

/*
 * @description: 通道：Channel
 *
 *      通道：顾名思义，连接两方或多方的道路，用于源节点于目标节点的连接，主要负责缓冲区数据的传输，通道本身不存储数据，所以需要
 *                  缓冲区配合
 *
 *              类似平常马路：它用于连接各个地方，但不负责其上的运输，都是由汽车 或者 人进行运输。
 *
 *      主要实现类：
 *          FileChannel
 *          SocketChannel
 *          ServerSocketChannel
 *          DatagramChannel
 *
 *      获取通道：
 *          1：getChannel();
 *              示例：FileInputStream fileInputStream = new FileInputStream("");
 *                   FileChannel channel = fileInputStream.getChannel();
 *
 *          2：open(); 静态方法
 *              示例：FileChannel.open(Paths.get(""));
 *
 *          3：Files.newByteChannel(Paths.get(""));
 *              Files - 工具类
 *
 *
 * @author: Liao
 * @date  2020/4/12 20:38
 */
public class FileCopyInChannelDemo {

    public static void main(String[] args) throws IOException {

        //FileChannel - getChannel(); - 非直接缓冲区
        //FIleChannelTest();

        //创建
        //openChannel();

        //FileChannel - open() - 内有直接缓冲 和 非直接缓冲，两者区别还是有些
        //FileChannelIsOpenTest();

    }

    private static void FileChannelIsOpenTest() throws IOException {
        /**
         * public static FileChannel open(Path path, OpenOption... options)
         *     throws IOException
         * {
         *     Set<OpenOption> set = new HashSet<OpenOption>(options.length);
         *     Collections.addAll(set, options);
         *     return open(path, set, NO_ATTRIBUTES);
         * }
         *
         * public enum StandardOpenOption implements OpenOption {}
         *
         * 第一个参数：一个URL - 使用 Paths.get() - Paths(工具类)
         * 第二个参数：使用 StandardOpenOption ， 该类是一个枚举，可以多个往后标记写
         *      比如如下：CREATE_NEW - 表示没有创建，有则失败，失败报错
         *                CREATE - 表示没有创建，有覆盖
         *
         *
         *      实测：使用直接缓冲区，文件大小没变，但内容发生了某种改变，即某些地方不显示
         *          测试：图片 src\liao\study\file\fileCopy\1.jpg 风景图 - 使用非直接缓冲
         *                  src\liao\study\file\fileCopy\2.jpg - 使用直接缓冲
         *
         *
         *      直接缓冲速度 >> 非直接缓冲
         *      直接缓冲安全 << 非直接缓冲
         */
        FileChannel inChannel = FileChannel.open(Paths.get("F:\\idea_job\\study\\src\\liao\\study\\file\\video.flv"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("F:\\idea_job\\study\\src\\liao\\study\\file\\fileCopy\\video.flv"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        Instant startTime =Instant.now();

        //使用非直接缓冲
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        while (inChannel.read(allocate) != -1) {
            allocate.flip();
            outChannel.write(allocate);
            allocate.clear();
        }

        inChannel.close();
        outChannel.close();

        //使用直接缓冲
/*        MappedByteBuffer inMapBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMapBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] bytes = new byte[2048];
        inMapBuffer.get(bytes);
        outMapBuffer.put(bytes);

        inChannel.close();
        outChannel.close();
*/
        Instant endTime =Instant.now();

        System.out.println("耗时：" + ChronoUnit.MILLIS.between(startTime, endTime) + " ms");
    }

    /**
     * 简写 - 但大致能懂，但实际 需要在 finally 中判断等
     *  getChannel();
     */
    private static void FIleChannelTest() {

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            inputStream = new FileInputStream("F:\\idea_job\\study\\src\\liao\\study\\file\\刀剑神域 序列之争.mp4");
            outputStream = new FileOutputStream("F:\\idea_job\\study\\src\\liao\\study\\file\\fileCopy\\刀剑神域 序列之争.mp4");

            inChannel = inputStream.getChannel();
            outChannel = outputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocateDirect(2048);

            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert inChannel != null;
                inChannel.close();

                assert outChannel != null;
                outChannel.close();

                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Paths - 工具类 JDK1.7
     * @throws IOException
     */
    public static void openChannel() throws IOException {
        //第一种
        FileInputStream fileInputStream = new FileInputStream("");
        FileChannel channel = fileInputStream.getChannel();

        //第二种
        FileChannel.open(Paths.get(""));

        //第三种
        Files.newByteChannel(Paths.get(""));
    }
}
