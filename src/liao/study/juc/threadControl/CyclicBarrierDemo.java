package liao.study.juc.threadControl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 * @description: 锁的控制 - CyclicBarrier
 *
 *      生活案例： 人齐了才开会
 *
 *      简介：控制多线程 - 自增计数器
 *              当线程到达计数器上限的时候，进行后续操作。
 *
 *      如果线程没有达到计数器所写的值，会一直阻塞，直到满足条件，这里需要注意。
 *
 *      其内的一些方法：
 *          cyclicBarrier.reset(); -- 初始化计数器，重新开始计数
 *
 * @author: Liao
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        /**
         * jdk源码。常用为传入两个参数
         *  概述：自增到多少线程，进行后续的操作(Runnable)
         *      参数一：线程上限
         *      参数二：线程需要执行的方法
         *
         *  public CyclicBarrier(int parties, Runnable barrierAction) {
         *         if (parties <= 0) throw new IllegalArgumentException();
         *         this.parties = parties;
         *         this.count = parties;
         *         this.barrierCommand = barrierAction;
         *     }
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println(Thread.currentThread().getName() + " 人到齐，正式开会"));

        for (int i = 1; i <= 10; i++) {
            new Thread(()-> {
                System.out.println(Thread.currentThread().getName() + " 人已进入");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }

}
