package liao.study.jvm.demo;

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
 *
 *
 *
 *      双亲委派
 *          双亲委派模式是在Java 1.2后引入的，其工作原理的是，如果一个类加载器收到了类加载请求，
 *          它并不会自己先去加载，而是把这个请求委托给父类的加载器去执行，如果父类加载器还存在其父类加载器，
 *          则进一步向上委托，依次递归，请求最终将到达顶层的启动类加载器，如果父类加载器可以完成类加载任务，
 *          就成功返回，倘若父类加载器无法完成此加载任务，子加载器才会尝试自己去加载，这就是双亲委派模式
 *
 *      简单来说：想找我，先找我爸，如果爸还有爷，则找我爸则先找我爷，以此类推
 *
 *
 *         该种做法好处：
 *              1：采用双亲委派模式的是好处是Java类随着它的类加载器一起具备了一种带有优先级的层次关系，
 *                  通过这种层级关可以避免类的重复加载，当父亲已经加载了该类时，就没有必要子ClassLoader再加载一次。
 *              2：其次是考虑到安全因素，java核心api中定义类型不会被随意替换，假设通过网络传递一个名为java.lang.Integer的类，
 *                  通过双亲委托模式传递到启动类加载器，而启动类加载器在核心Java API发现这个名字的类，发现该类已被加载，
 *                  并不会重新加载网络传递的过来的java.lang.Integer，而直接返回已加载过的Integer.class，
 *                  这样便可以防止核心API库被随意篡改。
 *              3：可能你会想，如果我们在classpath路径下自定义一个名为java.lang.SingleInterge类(该类是胡编的)呢？
 *                  该类并不存在java.lang中，经过双亲委托模式，传递到启动类加载器中，由于父类加载器路径下并没有该类，
 *                  所以不会加载，将反向委托给子类加载器加载，最终会通过系统类加载器加载该类。但是这样做是不允许，
 *                  因为java.lang是核心API包，需要访问权限，强制加载将会报出如下异常
 *
 *       作者：凯玲之恋
 *       链接：https://www.jianshu.com/p/9df9d318e838
 *       来源：简书
 *       著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 *
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
