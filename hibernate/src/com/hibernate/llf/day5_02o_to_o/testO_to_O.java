package com.hibernate.llf.day5_02o_to_o;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * Created by lenovo on 2016/5/3.
 */
public class testO_to_O {
    private SessionFactory sessionFacty = new Configuration()
            .configure()
            .addClass(Person.class)
            .addClass(Idcard.class)
            .buildSessionFactory();

    @Test
    public void testSave() throws Exception {
        Session session = sessionFacty.openSession();
        session.beginTransaction();

        Person person = new Person();
        person.setName("llf");

        Idcard idcard = new Idcard();
        idcard.setIdnum("123456789x");

        //关联并保存起来
        person.setIdcard(idcard);
        idcard.setPerson(person);

        session.save(person);
        session.save(idcard);

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testGet() throws Exception {
        Session session = sessionFacty.openSession();
        session.beginTransaction();
        //获取一方可以获取到关联的一方
//        Person person= (Person) session.get(Person.class,1);
//        System.out.println(person);
//        System.out.println(person.getIdcard());

        Idcard idcard = (Idcard) session.get(Idcard.class, 3);
        System.out.println(idcard);
        System.out.println(idcard.getPerson());

        session.getTransaction().commit();
        session.close();
    }

    //解除关系
    @Test
    public void testRemoveRelation() throws Exception {
        Session session = sessionFacty.openSession();
        session.beginTransaction();
//        //从有外间方解除  可以解除
//        Idcard idcard = (Idcard) session.get(Idcard.class, 3);
//        idcard.setPerson(null);

        //从没有外键的一方不能解除关系
        Person person = (Person) session.get(Person.class, 1);
        person.setIdcard(null);

        session.getTransaction().commit();
        session.close();
    }

    //测试删除
    @Test
    public void testDelete() throws Exception {
        // a, 如果没有关联的对方：能删除。
        // b, 如果有关联的对方且可以维护关联关系（有外键方），他就会先删除关联关系，再删除自己。
        // c, 如果有关联的对方且不能维护关联关系（无外键方），所以会直接执行删除自己，就会有异常。
        Session session = sessionFacty.openSession();
        session.beginTransaction();

//        Idcard idCard = (Idcard) session.get(Idcard.class, 2);
//        session.delete(idCard);

        Person person = (Person) session.get(Person.class, 2);
        session.delete(person);

        session.getTransaction().commit();
        session.close();


    }

}
