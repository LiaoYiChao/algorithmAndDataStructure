package liao.study.jvm.demo;

/*
 * @description: JVM - 方法区
 *
 *      方法区：存储了每一个类的结构信息，线程共享。
 *              方法区主要用来存储运行时常量池、静态变量、类信息、JIT编译后的代码等数据。
 *
 *      JDK1.8 去除永久代，改为元空间
 *          为什么：
 *              1：字符串在永久代中，容易出现性能问题和内存溢出。
 *              2：永久代会为CG带来不必要的复杂度，且回收效率偏低。
 *              3：两个版本JDK合二为一，常用的JDK为HotSpot，JDK1.8是HotSpot和JRockit合并的，
 *                  然而JRockit中没有永久代。
 *
 *      详情参考：
 *          https://blog.csdn.net/weixin_43320847/article/details/82950756 - 简介
 *          https://blog.csdn.net/u011635492/article/details/81046174 - 详解
 *
 * @author: Liao
 * @date  2020/4/15 21:44
 */
public class MethodAreaDemo {
}
