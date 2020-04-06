package liao.study.juc.threadType.executor.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * @description: Executors.newFixedThreadPool(int a);
 *                  创建自定义线程池数量的 线程池.
 *
 *     public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
       }
 *     JDK源码： 第五个参数：new LinkedBlockingQueue<Runnable>());
 *                  含义：创建了一个默认 Integer.MAX_VALUE 大小的有界阻塞队列，即可以接受最大值21亿阻塞线程进入。
 *                     允许的请求队列长度为 Integer.MAX_VALUE ，可能会堆积大量的请求，从而导致 OOM 。
 *
 *
 * @author: Liao
 * @date  2020/4/6 21:39
 */
public class FixedThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 执行了");
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}
