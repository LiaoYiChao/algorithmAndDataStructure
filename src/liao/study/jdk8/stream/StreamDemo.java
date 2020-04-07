package liao.study.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * @description: JDK8 流式编程  - 链式编程
 *                  简洁方便
 *
 *  两个包：java.util.function - Stream所使用的函数式编程包，内部为高度抽象的接口
 *          java.util.stream - 本体包，定义流操作
 *
 *      juf解析：
 *          内部主要有四大函数式接口：
 *                接口名称                      参数类型     返回类型    用途
 *
 *              Consumer<T> - 消费型接口          T           void    对类型为T的对象应用操作
 *                                                                      包含方法: void accept<T t>
 *
 *              Supplier<T> - 供给型接口          无           T       返回类型为T的对象
 *                                                                      包含方法: T get()
 *
 *              Function<T, R> - 函数型接口       T            R       对类型为T的对象应用操作，并返回结果。
 *                                                                   结果是R类型的对象。包含方法: R apply(T t)
 *
 *              Predicate<T> - 断定型接口         T            boolean   确定类型为T的对象是否满足某约束，并返回boolean值。
 *                                                                          包含方法: boolean test(T t)
 *
 *
 *
 * @author: Liao
 * @date  2020/4/7 18:31
 */
public class StreamDemo {

    public static void main(String[] args) {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User user6 = new User();
        User user7 = new User();

        List<User> list = Arrays.asList(user1,user2,user3,user4,user5,user6,user7);

        /**
         * 取出其中 Age < 50
         *  然后取出其中 Code 包含 b 的
         *    最后统一将筛选出来的值，将Id 改为 100
         */

        List<User> result = list.stream().filter(item -> item.getAge() < 50)
                                    .filter(item -> item.getCode().contains("b"))
                                    .peek((item) -> item.setId(100))
                                    .collect(Collectors.toList());

        result.forEach(System.out::println);
    }

}

class User {

    private Integer id;

    private String code;

    private Integer age;

    {
        this.setCode(UUID.randomUUID().toString().substring(0,4));
        this.setId(new Random().nextInt(1000));
        this.setAge(new Random().nextInt(100));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", age=" + age +
                '}';
    }
}
