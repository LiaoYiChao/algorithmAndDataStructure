package liao.study.juc.threadType.executor.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * @description: Executors.newSingleThreadExecutor();
 *                  创建一个 只允许一个线程进入的 线程池
 *
 *     public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
       }
 *      JDK源码：和FixedThreadPool 一样 new LinkedBlockingQueue<Runnable>()));
 *                  创建了一个可以接受21亿的有界阻塞队列 Integer.MAX_VALUE
 *                      允许的请求队列长度为 Integer.MAX_VALUE ，可能会堆积大量的请求，从而导致 OOM 。
 *
 * @author: Liao
 * @date  2020/4/6 21:47
 */
public class SingleThreadExecutorDemo {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> System.out.println(Thread.currentThread().getName() + " 执行了"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}
