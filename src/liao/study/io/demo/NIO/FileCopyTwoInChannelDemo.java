package liao.study.io.demo.NIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
 * @description: 使用 FileChannel 完成本地文件拷贝 - 2
 * @author: Liao
 * @date  2020/4/19 12:34
 */
public class FileCopyTwoInChannelDemo {

    public static void main(String[] args) throws Exception {

        String one = "./src/liao/study/io/四月是你的谎言.png";
        String one2 = "./src/liao/study/io/四月是你的谎言_2.png";

        String two = "./src/liao/study/io/demo/NIO/test.txt";
        String two2 = "./src/liao/study/io/demo/NIO/test2.txt";

        FileInputStream inputStream = new FileInputStream(new File(two));
        FileChannel inChannel = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream(new File(two2));
        FileChannel outChannel = outputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate(1024);

        while (true) {

            /**
             * 为什么要在这里加上 clear
             *  因为如果没有加上 clear position 的指针会和limit的指针值相等，而相等会返回0.而不是-1.则会一直循环不会跳出。
             *      原因是因为 当执行 flip方法的时候
             *      public final Buffer flip() {
             *         limit = position;
             *         position = 0;
             *         mark = -1;
             *         return this;
             *     }
             *     可以看出 position 赋值给了 limit，即limit = position
             *      当执行 write方法的时候 position = limit 即两者会相等。
             *
             *     public int write(ByteBuffer var1) throws IOException {
             *         this.ensureOpen();
             *         if (!this.writable) {
             *             throw new NonWritableChannelException();
             *         } else {
             *             synchronized(this.positionLock) {
             *                 int var3 = 0;
             *                 int var4 = -1;
             *
             *                 byte var5;
             *                 try {
             *                     this.begin();
             *                     var4 = this.threads.add();
             *                     if (this.isOpen()) {
             *                         do {
             *                             var3 = IOUtil.write(this.fd, var1, -1L, this.nd);
             *                         } while(var3 == -3 && this.isOpen());
             *
             *                         int var12 = IOStatus.normalize(var3);
             *                         return var12;
             *                     }
             *
             *                     var5 = 0;
             *                 } finally {
             *                     this.threads.remove(var4);
             *                     this.end(var3 > 0);
             *
             *                     assert IOStatus.check(var3);
             *
             *                 }
             *
             *                 return var5;
             *             }
             *         }
             *
             */
            //allocate.clear();
            int read = inChannel.read(allocate);
            if (read == -1) {
                break;
            }

            allocate.flip();
            outChannel.write(allocate);
        }

        inChannel.close();
        outChannel.close();
        inputStream.close();
        outputStream.close();

    }

}
