package liao.study.lockDemo;

/*
 * @description: 锁执行机制
 *                  学习自：尚硅谷-周阳-juc
 *
 *  1：标准访问，请问先打印邮件还是短信。
 *
 *  2：暂停四秒在邮件方法，请问先打印邮件还是短信。
 *
 *  3：新增普通sayHello方法，请问先打印邮件还是hello。
 *
 *  4：两部手机，请问先打印邮件还是短信。
 *
 *  5：两个静态同步方法，同一部手机，请问先打印邮件还是短信。
 *
 *  6：两个静态同步方法，两部手机，请问先打印邮件还是短信。
 *
 *  7：一个静态同步方法，一个普通同步方法，同一部手机，请问先打印邮件还是短信。
 *
 *  8：一个静态同步方法，一个普通同步方法，两部手机，请问先打印邮件还是短信。
 *
 * @author: Liao
 */
public class LockDemoEnforcementMechanism {

    public static void main(String[] args) {

        Phone phone = new Phone();

        new Thread(phone::sendEmail,"a").start();

        new Thread(phone::sendSMS, "b").start();
    }

}

class Phone {

    public synchronized void sendEmail() {
        System.out.println("发送邮件");
    }

    public synchronized void sendSMS() {
        System.out.println("发送短信");
    }

}
