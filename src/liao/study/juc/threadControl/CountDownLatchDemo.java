package liao.study.juc.threadControl;

import java.util.concurrent.CountDownLatch;

/*
 * @description: 锁的控制 - CountDownLatch
 *      生活案例：人走完关门
 *
 *      简介：控制多线程自减计数器
 *          该锁，使用一个计数器，使用 countDownLatch.countDown(); 方法进行计数器减一操作，当计数器为零的时候，
 *              开始执行后续操作，在计数器没有为零之前，使用 countDownLatch.await(); 阻塞线程。
 * @author: Liao
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        //表示 某个饭店还剩余5人
        CountDownLatch countDownLatch = new CountDownLatch(5);

        //5个线程 模拟5个人正在饭店进行
        for (int i = 0; i < 5; i++) {
            new Thread(()-> {
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + " 人已走");
            },String.valueOf(i)).start();
        }

        //当线程执行到这里，但发现计数器并没有为零，则阻塞并继续上述步骤，指到计数器为零
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " 客人已走完，关门");
    }

}
