package liao.study.juc.threadControl;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
 * @description: 多线程控制器 - Semaphore
 *
 *      对比CountDownLatch 和 CyclicBarrier ，Semaphore更为灵活，其即可增也可以减，
 *
 *      生活例子: 某个地点的座位，有5个，5个人做了后，其他人只能等其中某个让出位置后才能做。
 *
 * @author: Liao
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5);

        for (int i = 1; i <= 10; i++) {
            new Thread(()-> {
                try {
                    //等于抢占了一个位置
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢占了一个位置");

                    try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }

                    //人走了 释放了一个位置
                    System.out.println(Thread.currentThread().getName() + " 走了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }

}
