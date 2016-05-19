package com.hibernate.llf.day5_01sessionMethod;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;


/**
 * Created by lenovo on 2016/5/01.
 *
 * hibernate 的几种状态（临时状态，持久化状态，游离状态，删除状态
 */
public class TestUser {
    public static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");  //读取主配置文件
        cfg.addClass(User.class);
        sessionFactory = cfg.buildSessionFactory();
        //hibernate4.3的创建方法
//        StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
//        StandardServiceRegistry sr = srb.build();
//        sessionFactory = cfg.buildSessionFactory(sr);//通过配置文件创建配置文件

    }

    /**
     * save方法
     * save()：把临时状态变为持久化状态（交给Sessioin管理）
     * 会生成：insert into ...
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        //创建session工厂
        Session session = sessionFactory.openSession();
        //事务处理
        Transaction tx = session.beginTransaction();//开始事务
        User user = new User(); //临时状态
        user.setName("llf2");
        session.save(user);//变为持久化状态
        tx.commit();
        session.close();//关闭session

        user.setName("李四");  //游离状态   session关闭
        System.out.println(user.getName());
    }

    /**
     *  get()：获取数据，是持久化状态
     * 会生成：select ... where id=?
     * 会马上执行sql语句 （不然其他的方法要先commit提交后才执行）
     * 如果数据不存在，就返回null
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception {
        Session session= sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        User user= (User) session.get(User.class,4); //持久化！

        System.out.println(user.getClass());

        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println("who more fast!");  //在执行get后再输出
        System.out.println(user);
        tx.commit();
        session.close();
    }


    // update()：把游离状态变为持久化状态
    // 会生成：update ...
    // 在更新时，对象不存在就报错
    @Test
    public void testUpdate() throws Exception{
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        User user= (User) session.get(User.class,3);
        System.out.println(user.getName());//持久化状态

        session.clear();//清除session中所有的对象
//        session.evict(user);//清除某个指定的对象

        user.setName("ffzy4");
        session.update(user);
        session.flush();//刷新到数据库，可以在commit前直接操作到数据库
        System.out.println("--------");
        session.getTransaction().commit();
        session.close();
    }

    // saveOrUpdate()：把临时或游离状态转为持久化状态
    // 会生成：insert into 或 update ...
    // 在更新时，对象不存在就报错
    // 本方法是根据id判断对象是什么状态的：如果id为原始值（对象的是null，原始类型数字是0）就是临时状态，如果不是原始值就是游离状态。
    @Test
    public void testSaveOrUpdate() throws Exception{
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        User user= new User();
//        user.setId(6);  //id在数据库存在则执行update，没有回报错，因为id自增长，注释掉后执行时insert语句编译通过
        user.setName("kkkl");//自己生成一个游离状态对象
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }

    // delete()：把持久化或游离转为删除状态
    // 会生成：delete ...
    // 如果删除的对象不存在，就会抛异常
    @Test
    public void testDelete() throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // --------------------------------------------
         User user = (User) session.get(User.class, 6); // 持久化

//        User user = new User();
//        user.setId(3);

        session.delete(user);
//        session.flush();
        System.out.println("---");
        // --------------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    // load()：获取数据，是持久化状态（懒加载）
    // 会生成：select ... where id=?
    // load()后返回的是一个代理对象，要求类不能是final的，否则不能生成子类代理，就不能使用懒加载功能了。
    // 让懒加载失效的方式：一、把实体写成final的；二、在hbm.xml中写<class ... lazy="false">
    // 不会马上执行sql语句，而是在第1次使用非id或class属性时执行sql。
    // 如果数据不存在，就抛异常：ObjectNotFoundException
    @Test
    public void testLoad() throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // --------------------------------------------

        User user = (User) session.load(User.class, 2);
        System.out.println(user.getClass());  //代理对象
        System.out.println("---");
        System.out.println(user.getId());
        System.out.println(user.getName());
        // System.out.println(user.getName());

        // --------------------------------------------
        session.getTransaction().commit();
        session.close();
    }

    // 操作大量数据，要防止Session中对象过多而内存溢出
    @Test
    public void testBatchSave() throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // --------------------------------------------

        for (int i = 0; i < 30; i++) {
            User user = new User();
            user.setName("测试");
            session.save(user);

            if (i % 2 == 0) {
                session.flush(); // 先刷出
                session.clear(); // 再清空
            }
        }

        // --------------------------------------------
        session.getTransaction().commit();
        session.close();
    }



}
