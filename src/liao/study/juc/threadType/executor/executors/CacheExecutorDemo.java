package liao.study.juc.threadType.executor.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * @description: Executors.newCachedThreadPool - 缓存线程池Demo
 *                  Executors - 线程池工具类
 *                      CachedThreadPool - 创建一个缓存的线程池，
 *                          优点：该线程池特性，当访问量大时，会自动扩容线程池，当访问量小时，会自动减少线程池。
 *                          缺点：Integer.MAX_VALUE 该线程池能创建的最大线程数量，约21亿，无法控制并发数量，可能会出现OOM
 *                                  允许的创建线程数量为 Integer.MAX_VALUE ，可能会创建大量的线程，从而导致 OOM 。
 * @author: Liao
 * @date  2020/4/6 20:24
 */
public class CacheExecutorDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute((() -> {
                    System.out.println(Thread.currentThread().getName() + " 执行了");
                }));
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

}
