package liao.study.juc.threadType.executor;

import java.util.concurrent.*;

/*
 * @description: ThreadPoolExecutor - 重要(必须掌握)
 *                  该为所有线程池的底层创建池，Executors中的线程池都是基于该线程池创建.
 *
 *
        public ThreadPoolExecutor(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
            if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
                throw new IllegalArgumentException();
            if (workQueue == null || threadFactory == null || handler == null)
                throw new NullPointerException();
            this.acc = System.getSecurityManager() == null ?
                    null :
                    AccessController.getContext();
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.workQueue = workQueue;
            this.keepAliveTime = unit.toNanos(keepAliveTime);
            this.threadFactory = threadFactory;
            this.handler = handler;
        }
 *
 *      JDK源码：
 *          参数解析：
 *              参数一：int corePoolSize - 核心线程数量 (常驻线程)
 *              参数二：int maximumPoolSize - 最大线程数量
 *              参数三：long keepAliveTime - 线程最大空闲时间
 *                          (当前池线程超过核心线程数量，空闲时间达到设定值，销毁线程，只剩下核心线程数量为止)
 *              参数四：TimeUnit unit - 时间单位（泛型 TimeUnit.SECONDS 等）
 *              参数五：BlockingQueue<Runnable> workQueue - 线程任务等待队列，被提交但尚未执行的队列
 *              参数六：ThreadFactory threadFactory - 线程创建工厂，用于创建吸纳成，一般默认
 *              参数七：RejectedExecutionHandler handler - 拒绝策略
 *                          （当队列满了，并且工作线程 >= 线程池最大线程数 时如何拒绝）
 *
 *
 *          参数其他解析：
 *              参数五：BlockingQueue<Runnable> workQueue - 线程等待队列
 *                      其下常用的阻塞队列：
 *                          (常用)
 *                          ArrayBlockingQueue - 数组结构的有界阻塞队列
 *                              new ArrayBlockingQueue<>(2)); - 需要指定内部的队列长度
 *                          LinkedBlockingQueue - 链表结构的有界阻塞队列，默认队列长度 Integer.MAX_VALUE
 *                              new LinkedBlockingQueue<>();
 *
 *                          (了解)
 *                          LinkedBlockingDeque - 链表结构组成的双向阻塞队列
 *                              new LinkedBlockingDeque<>();
 *                          SynchronousQueue - 有且只有一个元素的单队列
 *                              new SynchronousQueue<>();
 *                          PriorityBlockingQueue - 支持优先级排序的无界阻塞队列
 *                              new PriorityBlockingQueue<>();
 *                          DelayQueue - 优先级队列实现的延迟无界阻塞队列
 *                              new DelayQueue<>();
 *
 *              参数六：ThreadFactory threadFactory - 线程创建工厂
 *                  后续完成。
 *
 *              参数七：RejectedExecutionHandler handler - 拒绝策略
 *                  后续完成。
 *
 *
 *      错误解析：
 *          java.util.concurrent.RejectedExecutionException
 *      错误原因：
 *          当设置的最大线程数量 + 可缓存的线程任务队列 < 涌入的线程数量 = RejectedExecutionException
 *              具体有两种情况，可参考下方链接.
 *      解决方案：
 *          1：设置拒绝策略
 *              JDK自带四种拒绝策略，默认使用 AbortPolicy
 *                  1：new ThreadPoolExecutor.AbortPolicy() (默认)
 *                      直接抛出异常策略(当线程达到设置的最大值时，超出即里面抛出异常，停止操作)
 *                  2：new ThreadPoolExecutor.CallerRunsPolicy()
 *                      该策略不会抛弃任务，也不会抛出异常，而是将任务回退到调用者。(即从哪来回哪去)
 *                  3：new ThreadPoolExecutor.DiscardOldestPolicy()
 *                      抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务。
 *                  4：new ThreadPoolExecutor.DiscardOldestPolicy()
 *                      直接丢弃无法处理的任务，不予任何处理也不抛出异常。(如可以允许任务丢失，该策略最佳)
 *          2：可参考 - https://blog.csdn.net/findmyself_for_world/article/details/49780587
 *          3：catch 抓捕错误，并进行对应操作。
 *
 * @author: Liao
 * @date  2020/4/6 21:53
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(1, 2, 1L,
                        TimeUnit.SECONDS, new ArrayBlockingQueue<>(3),
                        new ThreadPoolExecutor.DiscardOldestPolicy());

        try {
            for (int i = 0; i < 10; i++) {
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 执行了");
                });
            }
        } catch (RejectedExecutionException e) {
            System.out.println("捕捉到了错误");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

}
