package liao.study.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * @description: 可重入锁案例 - ReentrantLock - Demo
 *      高内聚低耦和   线程操作资源类
 * @author: Liao
 */
public class ReentrantLockDemoOne {

    public static void main(String[] args) {

        Resource resource = new Resource();

        for (int i = 0; i < 3; i++) {
            new Thread(()-> {
                while (true) {
                    int result = resource.countLetUp();
                    if (result == 0) {
                        break;
                    }
                }
            },String.valueOf(i)).start();
        }

    }

}

/**
 * 资源类 = 资源变量 + 资源方法
 *      高内聚
 */
class Resource{

    //资源类变量
    private int count = 30;

    //ReentrantLock 可重入锁 - 乐观锁（使用自旋锁实现 - 比较并替换）
    private Lock lock = new ReentrantLock();

    //资源类方法
    public int countLetUp() {

        lock.lock();
        try {
            if (count > 0) {
                System.out.println("当前变量值为: " + count-- + " , 剩余变量值为: " + count);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

        return count;
    }

}
