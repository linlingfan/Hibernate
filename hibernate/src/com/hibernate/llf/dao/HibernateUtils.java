package com.hibernate.llf.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * Created by lenovo on 2016/4/23.
 */
public class HibernateUtils {
    private static SessionFactory sessionFactory=null;
        static {
//            Configuration cfg=new Configuration();
//            读取默认位置的xml文件
//            cfg.configure();
//            读取指定位置的配置文件
//            cfg.configure("hibernate.cfg.xml");
//            初始化sesstonFactory
            // cfg.addResource("com/hibernate/llf/domain/User.hbm.xml");
            // cfg.addClass(User.class); // 去User类所在的包中查找名称为User，后缀为.hbm.xml的文件
            sessionFactory=new Configuration().configure().buildSessionFactory();
        }
    /**
     * 后去全局唯一的sessionFactory
     * @return
     */
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    /**
     * 获取全局唯一的sessionFactory中打开的一个session
     * @return
     */
    public static Session openSession(){
        return sessionFactory.openSession();
    }
}
