    线程池优势：
        线程池做的工作只要是控制运行的线程数量，处理过程中将任务放入队列，然后在线程创建后启动这些任务，
        如果线程数量超过了最大数量，超出数量的线程排队等待，等其他线程执行完毕，再从队列中取出任务来执行。
        
         
    线程池主要特点：线程复用；控制最大并发数；管理线程。
        
        
    线程池 - aliyun规范中 必须使用 ThreadPoolExecutor 进行线程池的手动创建。
      但在某些时刻，可以使用JDK自带的已写好的线程池。但需要谨慎使用。
      
      
      
      
      
      
      
      
      
      
      
      
      aliYun 1.3 文档摘录
      六：并发处理
          1. 【强制】获取单例对象需要保证线程安全，其中的方法也要保证线程安全。
            说明：资源驱动类、工具类、单例工厂类都需要注意。
          2. 【强制】创建线程或线程池时请指定有意义的线程名称，方便出错时回溯。
              正例：
              public class TimerTaskThread extends Thread {
              public TimerTaskThread() {
              super.setName("TimerTaskThread");
              ...
              }
          3. 【强制】线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
              说明：使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，解决资
              源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者
              “过度切换”的问题。
          4. 【强制】线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样
              的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。
              说明： Executors 返回的线程池对象的弊端如下：
              1） FixedThreadPool 和 SingleThreadPool :
              允许的请求队列长度为 Integer.MAX_VALUE ，可能会堆积大量的请求，从而导致 OOM 。
              2） CachedThreadPool 和 ScheduledThreadPool :
              允许的创建线程数量为 Integer.MAX_VALUE ，可能会创建大量的线程，从而导致 OOM 。
          5. 【强制】 SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为
              static ，必须加锁，或者使用 DateUtils 工具类。
              正例：注意线程安全，使用 DateUtils 。亦推荐如下处理：
              private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
              @ Override
              protected DateFormat initialValue() {
              return new SimpleDateFormat("yyyy-MM-dd");
              }
              };
              说明：如果是 JDK 8 的应用，可以使用 Instant 代替 Date ， LocalDateTime 代替 Calendar ，
              DateTimeFormatter 代替 SimpleDateFormat ，官方给出的解释： simple beautiful strong
              immutable thread - safe 。
          6. 【强制】高并发时，同步调用应该去考量锁的性能损耗。能用无锁数据结构，就不要用锁 ； 能
              锁区块，就不要锁整个方法体 ； 能用对象锁，就不要用类锁。
              说明：尽可能使加锁的代码块工作量尽可能的小，避免在锁代码块中调用 RPC 方法。
          7. 【强制】对多个资源、数据库表、对象同时加锁时，需要保持一致的加锁顺序，否则可能会造
              成死锁。
              说明：线程一需要对表 A 、 B 、 C 依次全部加锁后才可以进行更新操作，那么线程二的加锁顺序
              也必须是 A 、 B 、 C ，否则可能出现死锁。
          8. 【强制】并发修改同一记录时，避免更新丢失，需要加锁。要么在应用层加锁，要么在缓存加
              锁，要么在数据库层使用乐观锁，使用 version 作为更新依据。
              说明：如果每次访问冲突概率小于 20%，推荐使用乐观锁，否则使用悲观锁。乐观锁的重试次
              数不得小于 3 次。
          9. 【强制】多线程并行处理定时任务时， Timer 运行多个 TimeTask 时，只要其中之一没有捕获
              抛出的异常，其它任务便会自动终止运行，使用 ScheduledExecutorService 则没有这个问题。
          10. 【推荐】使用 CountDownLatch 进行异步转同步操作，每个线程退出前必须调用 countDown
              方法，线程执行代码注意 catch 异常，确保 countDown 方法被执行到，避免主线程无法执行
              至 await 方法，直到超时才返回结果。
              说明：注意，子线程抛出异常堆栈，不能在主线程 try - catch 到。
        
      
