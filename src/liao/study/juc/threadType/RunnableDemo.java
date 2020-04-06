package liao.study.juc.threadType;

/*
 * @description:
 * @author: Liao
 */
public class RunnableDemo {

    public static void main(String[] args) {

        MyRannable myRannable = new MyRannable();

        for (int i = 0; i < 10; i++) {
            new Thread(myRannable::run,String.valueOf(i)).start();
        }

    }

}

class MyRannable implements Runnable{

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " 执行了");

    }
}
