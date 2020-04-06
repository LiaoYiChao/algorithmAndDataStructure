package liao.study.juc.lockControl.lockDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * @description: 使用新的 Lock 进行
 *      多线程之间按顺序调用，实现 A -> B -> C
 *          三个线程启动。
 *
 *      AA 打印5次，BB打印10次，CC打印15次，进行10轮
 *
 * 尝试将三个打印 整合成一个动态的打印方法
 * @author: Liao
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {

        MyPrint myPrint = new MyPrint();

        new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                myPrint.print5();
            }
        },"A").start();

        new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                myPrint.print10();
            }
        },"B").start();

        new Thread(()-> {
            for (int i = 0; i < 5; i++) {
                myPrint.print15();
            }
        },"C").start();


    }

}

/**
 * 多线程编程：判断，干活，通知
 */
class MyPrint{

    // A-1 B-2 C-3
    private int number = 1;

    //创建一个非公平递归锁
    private Lock lock = new ReentrantLock();

    //创建三个监视器 - 每个监视器监视对应线程并进行对应工作。
    private Condition conditionOne = lock.newCondition();
    private Condition conditionTwo = lock.newCondition();
    private Condition conditionThread = lock.newCondition();

    /**
     *
     * @param printCount 打印次数
     * @param thisThreadCount 当前线程 - 编号
     * @param nextConditionCount 下一个通知线程 - 编号
     * @param thisThreadAwait 当前等待线程
     */
    public void myPrint(Integer printCount, Integer thisThreadCount, Integer nextConditionCount, Integer thisThreadAwait) {
        lock.lock();
        try {
            while (thisThreadCount != 1) {
                conditionOne.await();
            }

            for (int i = 0; i < printCount; i++) {
                System.out.println(Thread.currentThread().getName() + " 的线程的值；" + i);
            }

            number = 2;
            conditionTwo.signal();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //A线程 打印5次
    public void print5() {
        lock.lock();
        try {
            while (number != 1) {
                conditionOne.await();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " 的线程的值；" + i);
            }

            number = 2;
            conditionTwo.signal();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //B线程 打印10次
    public void print10() {
        lock.lock();
        try {
            while (number != 2) {
                conditionTwo.await();
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " 的线程的值；" + i);
            }

            number = 3;
            conditionThread.signal();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    //C线程 打印15次
    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                conditionThread.await();
            }

            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " 的线程的值；" + i);
            }

            number = 1;
            conditionOne.signal();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
