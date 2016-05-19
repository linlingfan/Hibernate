package com.hibernate.llf.day4_29o_to_m;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * Created by lenovo on 2016/4/29.
 */
public class TestDE {
    private static SessionFactory sessionFactory=new Configuration()
            .configure()
            .addClass(Department.class)//添加Hibernate实体类，这样可以不用在主配置一直配置映射地址
            .addClass(Employee.class)//添加Hibernate实体类
            .buildSessionFactory();

    /**
     * 保存关联关系数据
     */
    @Test
    public void testSave(){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        //创建对象
        Department department=new Department();
        department.setName("研发部");

        Employee employee1=new Employee();
        employee1.setName("张七");
        Employee employee2=new Employee();
        employee2.setName("李八");

        //关联起来
        employee1.setDepartment(department);
        employee2.setDepartment(department);

        //配置文件inverse="true" 后本方不维护关联，不需要再update语句维护。
        department.getEmployeeSet().add(employee1);
        department.getEmployeeSet().add(employee2);

        //保存  被依赖的前面，依赖的放后面保存，减少sql语句
        session.save(department);
        session.save(employee1);
        session.save(employee2);

        session.getTransaction().commit();
        session.close();
    }
    /**
     *  获取信息
     */
    @Test
    public void testGet() throws Exception{
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        Department department= (Department) session.get(Department.class,1);
        System.out.println("部门:"+department);
        System.out.println(department.getEmployeeSet().size());

        Employee employee= (Employee) session.get(Employee.class,1);
        System.out.println("员工:"+employee);
        System.out.println(employee.getDepartment());

        session.getTransaction().commit();
        session.close();
    }

/**
 * 解除关联关系
 *
 */
@Test
    public void testRemoveRelation(){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        //从员工方解除关联关系，就是改变外键的值
//        Department department=new Department();//重新关联
//        department.setId(2);
        Employee employee= (Employee) session.get(Employee.class, 2);//获取要接触关联的员工
        employee.setDepartment(null);

        //从部门方接触关联关系 （此时是维护关联关系，所以配置文件inverse="false"才能执行成功）
//        Department department= (Department) session.get(Department.class,2);
//        department.getEmployeeSet().clear();//清空employee
        session.getTransaction().commit();
        session.close();
    }

    /**
     * 删除
     */
    @Test
    public void testDelete(){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        //从员工这方删除
//        Employee employee= (Employee) session.get(Employee.class,3);
//        session.delete(employee);

        //从员工一方删除
        /**
         * 情况1，如果此部门米有对应的关联员工，可以直接删除
         *    2，如果有，而且配置文件inverse="false"时，会先把对应员工表内的外键update为null，再删除要删除部门
         *    3，如果有，而且配置文件inverse="true"时，编译报错。不能删除。因为部门不执行维护关联关系，就不能更新对应员工外键，使之变为空
         */
        Department department= (Department) session.get(Department.class,2);
        session.delete(department);

        session.getTransaction().commit();
        session.close();
    }


}
