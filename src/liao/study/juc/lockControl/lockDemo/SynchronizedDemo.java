package liao.study.juc.lockControl.lockDemo;

/*
 * @description: 判断/干活/通知
 *  题目：两个线程，操作初始值为零的变量。
 *      实现一个线程对该变量 + 1，一个线程对该变量 - 1。
 *
 *
 *      判断：多线程的判断，不能使用 if ，需要使用 while
 *          错误：A 线程的当前值：1
                 B 线程的当前值：2
                 D 线程的当前值：1
                    按照程序正常运行，其中因该为 0，1，0，1，0，1，但这里为1，2，1
 *          为什么：当多线程进行时，比如有四个线程进行加减操作，
 *              1：a增线程进入 增1 b增加线程等待，c，d减线程等待
 *              2：a增线程出去，b增线程进入，发现值已被修改，进行wait等待，c，d减线程等待。
 *              3：ab增线程在 if 中等待，因为没有出去，c减线程出来，d减线程等待。
 *              4：c减线程执行，值减，使用notifyAll，ab线程出来，但因为是if，而不是 while，导致ab两线程一直往下执行，
 *                  值增加两次，导致值为2。
 *
 * @author: Liao
 */
public class SynchronizedDemo {

    public static void main(String[] args) {

        Number number = new Number();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(5);
                    number.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(5);
                    number.reduction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(5);
                    number.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(5);
                    number.reduction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"D").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(5);
                    number.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"E").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(5);
                    number.reduction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"F").start();

    }

}

class Number {

    private static Integer number = 0;

    //线程自增
    synchronized void increment() throws Exception{
        //判断
        if (number != 0) {
            this.wait();
        }

        //干活
        System.out.println(Thread.currentThread().getName() + " 线程的当前值：" + number++);

        //通知
        this.notifyAll();
    }

    //线程自减
    synchronized void reduction() throws Exception{
        if (number == 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + " 线程的当前值：" + number--);
        this.notifyAll();
    }
}
