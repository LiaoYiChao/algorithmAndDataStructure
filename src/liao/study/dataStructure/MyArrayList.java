package liao.study.dataStructure;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/*
 * @description:
 *      ArrayList
 *          - 底层 Object数组
 *          - 初始值为10
 *          - 扩容机制 - Arrays.copyOf
 *              原值的一半，如果为23之类的数，/2 取整，扩容
 * @author: Liao
 * @data 2020/4/3 21:56
 */
public class MyArrayList {

    public static void main(String[] args) {

//        threadInsecurityByArrayListDemo();
//
//        vectorForThreadSecurity();
//
//        thraedSecurityByArrayList();
//
        copyOnWriteArrayListForThreadSecurity();
    }

    /**
     * CopyOnWriteArrayList 线程安全
     *  本质：制作一个副本，写操作在副本，且有且只有一个能进行写操作，读操作在之前的引用中，当写操作完成，引用指向写完成后的，以此类推
     */
    private static void copyOnWriteArrayListForThreadSecurity() {
        List<String> list = new CopyOnWriteArrayList<>();
        listThreadMethod(list);
    }

    /**
     * Vector 线程安全
     *  本质：使用了重锁 synchronized ，现不推荐
     */
    private static void vectorForThreadSecurity() {
        List<String> list = new Vector<>();
        listThreadMethod(list);
    }

    /**
     * 线程安全 - Collections.synchronizedList(new ArrayList<>());
     *      集合工具类中的 synchronizedList 本质是通过synchronized
     * @return
     */
    private static void thraedSecurityByArrayList() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        listThreadMethod(list);
    }

    /**
     * 线程不安全演示
     *  异常 - java.util.ConcurrentModificationException
     *      线程并发异常
     */
    private static void threadInsecurityByArrayListDemo() {
        List<String> list = new ArrayList<>();
        listThreadMethod(list);
    }

    //公用模块
    private static void listThreadMethod(List<String> list) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 6));
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

}
