package liao.study.wrtitten.java;

/*
 * @description: 牛客刷题 - 笔记
 * @author: Liao
 * @date  2020/4/16 19:13
 */
public class demo {

    private static final int a;

    static {
        a = 2;
    }

    public static int aMethod(int i)throws Exception
    {
        try{
            return i / 10;
        }
        catch (Exception ex)
        {
            throw new Exception("exception in a Method");
        } finally{
            System.out.printf("finally");
        }
    }

    public static void main(String [] args)
    {
        Thread thread = new Thread(() -> {
            System.out.println("hello");
        });

        /**
         * run 和 start 的区别
         *  run - 不会开启一条新的线程，而是在当前线程上完成。
         *  start - 开启一条新的线程，进入就绪状态，由CPU调度后进行。
         */
        thread.run();
        thread.start();
    }


}
