package liao.study.jvm;

/*
 * @description:
 *
 *      类 --> xx.class (class文件) --> ClassLoader --> JVM --> xx Class (模板)
 *           new xxx(); 操作 通过模板进行。
 *
 *      JVM - 类加载器 - Class loader
 *          在JVM 中（JDK8），总共有四种类加载器
 *              1：Bootstrap Class Loader - 启动类加载器
 *                  1.1：即如 Object，String等，都是通过该加载器在一开始就将这类Class 加载进入了 JVM，所以才能在一开始直接使用。
 *                  1.2：该加载器也叫根加载器，主要加载 $JAVA_HOME/jre/lib/rt.jar
 *
 *              2：Extension Class Loader - 扩展类加载器
 *                  2.1：Java发展了很长历史，在该过程中诞生了很多其他的包,类,而这些新诞生的不会加入到上述的rt.jar中，即启动类加载器中，
 *                          而是做为扩展类在扩展加载器中进行。
 *                  2.2：类比 java.xxx.xxx 和 javax.xxx.xxx
 *
 *              3：AppClassLoader Class Loader - 用户自己编写类加载器
 *                  3.1：用户自己编写的类，一般都在此进行加载。
 *
 *              4：ClassLoader Class Loader - 用户定制类的加载方式（自定义类加载器）
 *
 *
 *              加载顺序：1 > 2 > 3 > 4
 *
 * @author: Liao
 * @date  2020/4/8 18:24
 */
public class ClassLoaderDemo {

    public static void main(String[] args) {

        /**
         * 由启动类加载器 进行加载
         *
         * o.getClass().getClassLoader()
         *
         *  getClass() = 获取JVM中的类模板
         *  getClassLoader() = 获取该类模板的 类加载器
         */
        Object o = new Object();
        System.out.println(o.getClass().getClassLoader()); //null 可能因为自己是最大的,具体原因暂不知


        System.out.println();
        System.out.println();
        System.out.println();


        /**
         * 用户自己编写的 由 AppClassLoader 进行
         * 输出结果
         *
         * sun.misc.Launcher$AppClassLoader@b4aac2
         * sun.misc.Launcher$ExtClassLoader@16d3586
         * null
         *
         * 为null 理论上是因为自己不会去弄自己，具体原因还需细细查找。
         */
        ClassLoaderDemo classLoaderDemo = new ClassLoaderDemo();
        System.out.println(classLoaderDemo.getClass().getClassLoader()); //获取自己的类加载器
        System.out.println(classLoaderDemo.getClass().getClassLoader().getParent()); //获取父的类加载器
        System.out.println(classLoaderDemo.getClass().getClassLoader().getParent().getParent()); //获取父的父的类加载器
    }

}
