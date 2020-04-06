package liao.study.juc.threadType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/*
 * @description:
 *  线程实现的方式： implements Callable<>

 *
 *
 * @author: Liao
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //没传参数
        FutureTask<String> task = new FutureTask(new MyCallable());
        new Thread(task,"A").start();
        String result = task.get();
        System.out.println(result);

        //传参数
        MyCallableToParameter myCallableToParameter = new MyCallableToParameter(1,2);
        FutureTask<Integer> integerFutureTask = new FutureTask<>(myCallableToParameter);
        new Thread(integerFutureTask,"B").start();
        Integer integer = integerFutureTask.get();
        System.out.println(integer);

        //可以该中写法
        //FutureTask<Integer> integerFutureTask1 = new FutureTask<>(new MyCallableToParameter(1, 2));

        //使用线程池方式
        List<FutureTask<Integer>> taskList = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            //创建一个List 用于放置任务，放置10个任务
            taskList.add(new FutureTask<>(new MyCallableToParameter(1,2)));
        }

//        Executor executor = new ThreadPoolExecutor(10, 20, 0,
//                                                        TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
//
//        taskList.forEach(executor::execute);


    }

}

//实现Callable - 有返回值
class MyCallable implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("Callable Impl");
        return "hello callable";
    }
}

class MyCallableToParameter implements Callable<Integer> {

    private Integer a;

    private Integer b;

    public MyCallableToParameter(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer call() throws Exception {
        return a + b;
    }
}
