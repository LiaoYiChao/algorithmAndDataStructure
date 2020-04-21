package liao.study.io.demo.NIO;

import java.nio.ByteBuffer;

/*
 * @description:
 *
 *      Buffer - 根据数据类型，提供了相应类型的缓冲区
 *          ByteBuffer - 最常用
 *          CharBuffer
 *          ShortBuffer
 *          IntBuffer
 *          LongBuffer
 *          FloatBuffer
 *          DoubleBuffer
 *              (没有 Boolean)
 *
 *
 *  ByteBuffer JDK源码。继承于Buffer
 * public abstract class ByteBuffer extends Buffer implements Comparable<ByteBuffer>
 *
 * public abstract class Buffer {
 *      private int mark = -1;
        private int position = 0;
        private int limit;
        private int capacity;
    }
 *
 *     Invariants: mark <= position <= limit <= capacity
 *
 *      上述为Buffer 中的核心值
 *          mark：记录 position 的当前位置，通过 ByteBuffer.mark() 进行记录，reset() 恢复到记录位置
 *
 *          position：缓冲区正在操作数据的位置
 *
 *          limit：缓冲区的界限，即缓冲区中操作数据的大小，如创建了一个边界为 1024的缓冲区，但内部数据只有30，则limit为30，最多读取到
 *                  30的位置，不可往后。
 *
 *          capacity：缓冲区中最大存储数据量，声明后不可修改
 *
 *              演示：请运行下述代码
 *
 * @author: Liao
 * @date  2020/4/11 13:04
 */
public class BufferDemo {

    public static void main(String[] args) {

        ByteBufferApi();

    }

    /**
     *      Api：
     *          A: 获取Buffer：xxxBuffer.allocate(int capacity);
     *                      该获取的是 非直接缓冲的 Buffer
     *
     *                     xxxBuffer.allocateDirect(int capacity);
     *                      获取的是 直接缓冲的 Buffer
     *
     *              两者区别：
     *                  前提：物理磁盘 - (read) -> 内核地址空间(缓存)
     *                          - (copy) -> 用户地址空间 - (read) -> 应用程序
     *                          - (write) -> 用户地址空间 - (copy) -> 内核地址空间
     *                          - (write) -> 物理磁盘
     *                  解释：当应用程序写入一个东西到磁盘，一般是需要先发起一个写入指令，
     *                          用户地址空间接受到数据，完整copy一份到内核地址空间，然后由
     *                          内核地址空间进行写入到物理磁盘。
     *
     *                  1：非直接缓冲：即上述演示的就是非直接缓冲，有一个中间带隔离进行
     *                      直接缓冲：没有上述，双方直接映射到一块内存中，没有多余操作。
     *
     *                  生活举例：非直接缓冲，有中间商，我信任中间商，他也信任，虽然效率低了些，但
     *                              保证安全。
     *                          直接缓冲：没有中间商，由双方直接对接，虽然效率提高，但亦存在一定风险。
     *
     *          put() - 存入
     *
     *          flip() - 切换为读模式
     *
     *          get() - 取出
     *
     *          clear() - 非真删除，类似软删除，即标记为丢弃状态，但还是可以读取
     *
     *          rewind() - 重复读，原理 position 归于第一位
     *
     *          异常：
     *              1：java.io.BufferUnderflowException
     *                  出现状况：byte[] bytes = new byte[2048];
     *                          buffer.get(bytes);
     *                    当在 new byte[] --- []号中传入了一个自定的值，但该值超出或小于buffer.limit 都是出现该异常，
     *                                          所以建议内部填写：ByteBuffer.limit()
     *
     */
    public static void ByteBufferApi() {

        //创建一个 ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //put() 存入一个 byte[]
        String a = "hello buffer";
        buffer.put(a.getBytes());
        System.out.println("put() 指针的位置：");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("---------------");
        System.out.println();
        System.out.println();

        buffer.flip();              //切换为读模式



        //get 读取其中的内容，用一个byte接受
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        System.out.println(new String(bytes, 0, bytes.length));
        System.out.println("get() 指针位置：");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("---------------");
        System.out.println();
        System.out.println();


        buffer.rewind();
        System.out.println("rewind() 指针的位置：");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("---------------");
        System.out.println();
        System.out.println();


        buffer.clear();
        System.out.println("clear() 指针的位置：");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("---------------");
        System.out.println();
        System.out.println();


        //清空了 还是存在, 但对应的指针值已经初始化，具体观看 clear源码
        byte[] bytes2 = new byte[buffer.limit()];
        buffer.get(bytes2);
        System.out.println(new String(bytes2, 0, bytes2.length));

    }

}
