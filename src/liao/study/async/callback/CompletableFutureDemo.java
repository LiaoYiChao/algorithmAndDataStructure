package liao.study.async.callback;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*
 * @description: 异步执行 - CompletableFuture
 * @author: Liao
 * @date  2020/4/7 22:30
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * Void - void 的包装类，表示没有返回值
         *
         *     public static CompletableFuture<Void> runAsync(Runnable runnable) {
         *         return asyncRunStage(asyncPool, runnable);
         *     }
         *
         *     CompletableFuture.runAsync(() -> {});
         *      需要传入一个 Runnable 接口，但因为其是函数式接口，可以使用函数式编程方式。
         *
         * 该操作表示，异步执行任务，没有返回值。
         *      类似 我指定任务流程，但并不关心结果。
         *
         */
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " 有返回值线程");
        });




        /**
         * 可获得返回值的 异步
         *
         * 正常情况下
         *  ForkJoinPool.commonPool-worker-1 有返回值线程
         *  ForkJoinPool.commonPool-worker-1 有返回值线程 - 1
         *  succeed: Hello CompletableFuture
         *  error: null
         *  Hello CompletableFuture
         *
         *
         * 异常情况下
         *  ForkJoinPool.commonPool-worker-1 有返回值线程
         *  ForkJoinPool.commonPool-worker-1 有返回值线程 - 1
         *  succeed: null
         *  error: java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
         *  main ,有返回值线程 - 2
         *  error
         *
         */
        CompletableFuture<String> uCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " 有返回值线程 - 1");

            //手动异常
            int a = 10 / 0;

            return "Hello CompletableFuture"; })

        .whenComplete((succeed, error) -> {
            //第一个参数表示 成功显示
            System.out.println("succeed: " + succeed);
            //第二个参数表示 失败显示
            System.out.println("error: " + error); })

        .exceptionally(errorDispose -> {
            //errorDispose 如果执行失败 则进入这里，并可以设置对应的失败返回
            System.out.println(Thread.currentThread().getName() + " ,有返回值线程 - 2");
            return "error";
        });

        //CompletableFuture<T>.get() 可以获取返回值
        System.out.println(uCompletableFuture.get());

    }

}
