package com.hibernate.llf.day4_30m_to_m;

import com.hibernate.llf.day4_29o_to_m.Department;
import com.hibernate.llf.day4_29o_to_m.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * Created by lenovo on 2016/4/29.
 */
public class TestTS {
    private static SessionFactory sessionFactory = new Configuration()
            .configure()
            .addClass(Teacher.class)//添加Hibernate实体类，这样可以不用在主配置一直配置映射地址
            .addClass(Student.class)//添加Hibernate实体类
            .buildSessionFactory();

    /**
     * 保存关联关系数据
     */
    @Test
    public void testSave() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //创建对象
        Teacher teacher1 = new Teacher();
        teacher1.setName("林老师");
        Teacher teacher2 = new Teacher();
        teacher2.setName("李老师");

        Student student1 = new Student();
        student1.setName("张七");
        Student student2 = new Student();
        student2.setName("李八");

        // 关联起来
        student1.getTeacherSet().add(teacher1);
        student1.getTeacherSet().add(teacher2);
        student2.getTeacherSet().add(teacher1);
        student2.getTeacherSet().add(teacher2);

        teacher1.getStudentSet().add(student1);
        teacher1.getStudentSet().add(student2);
        teacher2.getStudentSet().add(student1);
        teacher2.getStudentSet().add(student2);

        // 保存
        session.save(student1);
        session.save(student2);
        session.save(teacher1);
        session.save(teacher2);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * 获取信息
     */
    @Test
    public void testGet() throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Teacher teacher = (Teacher) session.get(Teacher.class, 3);
        System.out.println("老师:" + teacher);
        System.out.println(teacher.getStudentSet());

        Student student = (Student) session.get(Student.class, 1);
        System.out.println("学生:" + student);
        System.out.println(student.getTeacherSet());

        session.getTransaction().commit();
        session.close();
    }

    /**
     * 解除关联关系
     */
    @Test
    public void testRemoveRelation() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //从学生方解除关联关系，删除了中间表的对应关系
        Student student = (Student) session.get(Student.class, 3);//获取要接触关联的员工
        student.getTeacherSet().clear();
//        student.setTeacherSet(null);  也可以
        //从教师方接触关联关系 （此时是维护关联关系，所以配置文件inverse="false"才能执行成功）
        // 如果inverse=false就可以解除，如果为true就不可以解除
        Teacher teacher = (Teacher) session.get(Teacher.class, 3);
        teacher.getStudentSet().clear();//清空
        session.getTransaction().commit();
        session.close();
    }

    /**
     * 删除
     */
    @Test
    public void testDelete() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        /**
         // a, 如果没有关联的对方：能删除。
         // b, 如果有关联的对方且inverse=false，由于可以维护关联关系，他就会先删除关联关系，再删除自己。
         // c, 如果有关联的对方且inverse=true，由于不能维护关联关系，所以会直接执行删除自己，就会有异常。
         */
        Teacher teacher = (Teacher) session.get(Teacher.class, 4);
        session.delete(teacher);

        session.getTransaction().commit();
        session.close();
    }


}
