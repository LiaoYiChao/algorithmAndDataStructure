package liao.study.juc.lockControl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * @description: ReadWriteLock - 读写锁
 *      读 - 并发读取
 *      写 - 只可一个
 *          保证可以共同读，但不可共同写
 *
 *      ReentrantReadWriteLock 实现了 ReadWriteLock 并添加了可重入等特性
 *
 *      生活中例子：因疫情，去每个小区都需要登记，不可能他人只写了一个名字，笔就被他人拿去用了，必须等该人填写完善后在交予他人笔。
 *
 *      读读 - 并发
 *      读写 - 单个
 *      写写 - 单个
 * @author: Liao
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyReadAndWriteLock lock = new MyReadAndWriteLock();

        for (int i = 0; i < 5; i++) {
            new Thread(lock::housingEstateRegister,String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(lock::RegisterMessageLook,String.valueOf(i)).start();
        }

    }
}

/**
 * 模拟疫情 - 登记情况，可以同时看，但只可一个写
 */
class MyReadAndWriteLock{

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // 登记个人信息，只可一个
    public void housingEstateRegister() {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始登记个人信息");
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "登记完成");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }

    // 多人观看登记信息
    public void RegisterMessageLook() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 在观看");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }
}
