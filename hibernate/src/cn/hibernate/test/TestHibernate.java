package cn.hibernate.test;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * Created by lenovo on 2016/5/5.
 */
public class TestHibernate {

    @Test
    public void testFun() throws Exception {
        SessionFactory sessionFactory = new Configuration()
                .configure()
                .addClass(Department.class)//添加Hibernate实体类，这样可以不用在主配置一直配置映射地址
                .addClass(Employee.class)//添加Hibernate实体类
                .addClass(Privilege.class)
                .addClass(UserAccount.class)
                .buildSessionFactory();

    }

}
