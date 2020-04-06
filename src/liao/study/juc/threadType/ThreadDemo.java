package liao.study.juc.threadType;


/*
 * @description:
 * @author: Liao
 */
public class ThreadDemo {

    public static void main(String[] args) {

        MyThread myThread = new MyThread();

        for (int i = 0; i < 10; i++) {
            new MyThread().start();
        }

    }

}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 执行了");
        super.run();
        //super.setName(""); //设置线程名称，aliYun规范
    }
}
