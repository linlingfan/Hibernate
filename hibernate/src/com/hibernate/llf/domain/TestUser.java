package com.hibernate.llf.domain;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;


/**
 * Created by lenovo on 2016/4/21.
 */
public class TestUser {
    public static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");  //读取主配置文件
        //hibernate4.3的创建方法
//        StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
//        StandardServiceRegistry sr = srb.build();
//        sessionFactory = cfg.buildSessionFactory(sr);//通过配置文件创建配置文件
        sessionFactory = cfg.buildSessionFactory();


    }

    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.setName("llf");

        //创建session工厂
        Session session = sessionFactory.openSession();
        //事务处理
        Transaction tx = session.beginTransaction();//开始事务
        session.save(user);
        tx.commit();//提交事务
        session.close();//关闭session
    }

    @Test
    public void testGet() throws Exception {
        Session session= sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        User user= (User) session.get(User.class,1);
        System.out.println(user);
        tx.commit();
        session.close();
    }

}
