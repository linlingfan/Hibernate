package com.hibernate.llf.day5_02_HQL;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by lenovo on 2016/5/9.
 */
public class TestHQL {
    private SessionFactory sessionFactory = new Configuration()
            .configure()
            .addClass(Department.class)
            .addClass(Employee.class)
            .buildSessionFactory();

    /**
     * 插入测试数据
     */
    @Test
    public void testHQL_Save() throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //保存一些部门数据
        for (int i = 1; i <= 10; i++) {
            Department dep = new Department();
            dep.setName("llf" + i);
            session.save(dep);
            Employee employee = new Employee();
            employee.setName("fzy" + i);
            employee.setDepartment(dep);
            session.save(employee);
        }

//        for(int i=1;i<=10;i++){
//            Employee employee=new Employee();
//            employee.setName("fzy"+i);
//            session.save(employee);
//        }

        session.getTransaction().commit();
        session.close();
    }

    /**
     * 测试HQL的查询
     * 和sql语句类似 语法基本上都可以直接使用。
     * SQL查询的是表和表中的列；HQL查询的是对象与对象中的属性
     * HQL的关键字不区分大小写，类名与属性名是区分大小写的
     * SELECT可以省略.
     */
    @Test
    public void testHQL_select() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        String HQL = null;
        //1.简单查询
//        HQL="From Department";
//        HQL="FROM Department d";//使用别名 as可以省略
//        HQL = "FROM Department as d";//使用别名

        //2.待条件查询
//        HQL="FROM Department d where d.id>5 and d.id<10";

        //3.排序条件
//        HQL="FROM Department d where d.id<10 order by d.id desc ";
//        HQL = "FROM Department d where d.id<10 order by d.name desc ,d.id asc ";

        //4.指定select子句（不可以使用select *）
//        HQL="select e FROM Department e";//相当于"FROM Employee e"  不过一定要指定别名，不然会报错
//        HQL = "select e.name from Department e";// 只查询一个列，返回的集合的元素类型就是这个属性的类型,可以直接输出
//        HQL = "select e.name,e.id  from Department e";// 查询多个列，返回的集合的元素类型是Object数组,此时输出要判断是否为数组，然后使用相应输出
        HQL = "select new Department(d.id,d.name) from Department d";// 可以使用new语法，指定把查询出的部分属性封装到对象中,前提是Department支持d.name, d.id的构造函数,(一定要对应的构造函数)


        //5.执行查询，获得结果（list、uniqueResult、分页 ）
        // Query query = session.createQuery("FROM Employee  where id=11");
        // List list = query.list();//查询结果是一个list集合
        // query.setFirstResult(0);
        // query.setMaxResults(10);
//         Employee employee = (Employee) query.uniqueResult();// 查询的结果是唯一的一个结果，当结果有多个，就会抛异常
//         System.out.println(employee);

        //6，方法链
        List list = session.createQuery(HQL)
//                .setFirstResult(0)
//                .setMaxResults(4)
                .list();
        
        for (Object obj : list) {
            if (obj.getClass().isArray()) {
                System.out.println(Arrays.toString((Object[]) obj));
            } else
                System.out.println(obj.toString());
        }

        tx.commit();
        session.close();
    }

    @Test
    public void testHQL_other(){
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        //1，聚集函数：count(), max(), min(), avg(), sum()

        String hql=null;
//        hql="select count(*) from Department";
//        hql="select min(id) from Department";

//        Number number= (Number) session.createQuery(hql).uniqueResult();
//        System.out.println(number.getClass());  //long型的count(*)
//        System.out.println(number);

        //2.分组: Group By ... Having

//        hql="select e.name,count(e.id) FROM Department e group by e.name";
//        hql= "select e.name,count(e.id) FROM Department e group by e.name HAVING count(e.id)>1";
//----------------
//         hql = "SELECT e.name,COUNT(e.id) AS c " + //
//         "FROM Employee e " + //
//         "WHERE id<19 " + //
//         "GROUP BY e.name " + //
//         "HAVING count(e.id)>1 " + // 在having子句中不能使用列别名
//         "ORDER BY c ASC"; // 在orderby子句中可以使用列别名

        // 3，连接查询 / HQL是面向对象的查询

        //>>内连接  关键字inner可以省略
//        hql="select e.name ,e.id ,d.name from Employee e inner join e.department d";//e.department面向对象
        //>>左连接 关键字outer可以省略(外连接)
//        hql= "select e.name ,e.id ,d.name from Employee e left outer join e.department d";
        //>>右连接
//        hql = "select e.name ,e.id ,d.name from Employee e right outer join e.department d";
        // 可以使用更方便的方法
//         hql = "SELECT e.id,e.name,e.department.name FROM Employee e";

        // 4，查询时使用参数
        // >> 方式一：使用'?'占位
//        hql="from Employee e where e.id between ? and ?";
//        List list=session.createQuery(hql)
//                .setParameter(0,11)  //第一个占位符？ 值为11
//                .setParameter(1, 15)
//                .list();

        // >> 方式二：使用变量名
//        hql= "from Employee e where e.id between :idMin and :idMax";
//        List list =session.createQuery(hql)
//                .setParameter("idMin", 10)
//                .setParameter("idMax", 16)
//                .list();

        // 当参数是集合时，一定要使用setParameterList()设置参数值
//        hql="from Department e where e.id in (:ids)";
//        List list=session.createQuery(hql)
//                .setParameterList("ids", new Object[]{1, 2, 3, 4, 5, 12})
//                .list();


        // 5，使用命名查询?????
//         Query query = session.getNamedQuery("queryByIdRange");
//         query.setParameter("idMin", 3);
//         query.setParameter("idMax", 10);
//         List list = query.list();


        // 6，update与delete，不会通知Session缓存
        // >> Update 更新
//        int result1=session.createQuery(
//                "update Employee e set e.name=? where e.id=15")
//                .setParameter(0,"hhhh")
//                .executeUpdate();  // 返回int型的结果，表示影响了多少行。
//        System.out.println("result:"+result1);
        //>>delete 删除
        int result2 = session.createQuery(
                "delete from Employee e where e.id>18")
                .executeUpdate();  // 返回int型的结果，表示影响了多少行。
        System.out.println("result:" + result2);


//        List list=session.createQuery(hql).list();
//        for (Object obj : list) {
//            if (obj.getClass().isArray()){
//                System.out.println(Arrays.toString((Object[]) obj));
//            }else
//                System.out.println(obj.toString());
//        }

        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testHql_DML() throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // --------------------------------------------

        // 第一次显示名称
        Employee employee = (Employee) session.get(Employee.class, 13);
        System.out.println(employee.getName());

        // update与delete，不会通知Session缓存
        int result = session.createQuery(//
                "UPDATE Employee e SET e.name=? WHERE id>12")//
                .setParameter(0, "无名氏3")//
                .executeUpdate(); // 返回int型的结果，表示影响了多少行。

        // 第二次显示名称
        // 在update或delete后，需要refresh(obj)一下以获取最新的状态
        session.refresh(employee);
        System.out.println(employee.getName());

        // --------------------------------------------
        session.getTransaction().commit();
        session.close();
    }


}
