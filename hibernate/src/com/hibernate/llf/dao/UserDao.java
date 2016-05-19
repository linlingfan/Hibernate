package com.hibernate.llf.dao;

import com.hibernate.llf.domain.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by lenovo on 2016/4/23.
 * <p/>
 * hibernate的几个数据库操作模板
 */
public class UserDao {
    /**
     * 保存save
     */
    public void save(User user) {
        Session session = HibernateUtils.openSession();
        try {
            /**
             * 开始事务
             * session.save操作
             * 提交事务
             * 回滚事务
             * 关闭事务
             * 关闭session
             */
            Transaction tx = (Transaction) session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();//事务回滚
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * 更新
     *
     * @param user
     */
    public void update(User user) {
        Session session = HibernateUtils.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(int id) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);//先获取User对象
            session.delete(user);//删除的是实体对象
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * 根据id查询user
     *
     * @param id
     * @return
     */
    public User getById(int id) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            tx.commit();
            return user;
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * 查询所有的信息
     *
     * @return
     */
    public List<User> findAll() {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            //第一种使用HQL查询 类似sql
            List<User> userList = session.createQuery("FROM User").list();
            //使用Criteria查询
//            Criteria criteria=session.createCriteria(User.class);
//            List<User> List= criteria.list();
            tx.commit();
            return userList;
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    /**
     * 分页查询
     *
     * @param firstResult 从结果列表中那个索引开始读取数据
     * @param maxResult   最多读取多少数据
     * @return
     */
    public PageBean findAll(int firstResult, int maxResult) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try{
                tx=session.beginTransaction();
            //方式一 查询一页的数据
//           org.hibernate.Query query=  session.createQuery("FROM User");
//            query.setFirstResult(firstResult);
//            query.setMaxResults(maxResult);
//            List<User> list=query.list();
            //方法二 方法链
            List<User> userList=session.createQuery(
                    "FROM User")
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResult)
                    .list();
            //查询总记录数  Long 等数据类型都是继承Number
            Long count = (Long) session.createQuery("select count(*) from User")
                    .uniqueResult();
            tx.commit();
            //返回  已经有有参构造器 不用再set进去直接调用构造器
            return new PageBean(count.intValue(),userList);
        } catch(Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        }finally {
            session.close();
        }
    }


}

