package com.tgs.warehouse.dao;

import com.tgs.warehouse.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tgs.warehouse.entities.User;

import javax.persistence.TypedQuery;

/**
 * Created by dana on 4/18/2017.
 */
public class UserDAO {

    public User searchUser(String username) {

        User user = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            Transaction transaction = session.beginTransaction();
            String hql = "select u from User u where lower(u.username) like lower(:username) ";
            TypedQuery<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", "%" + username + "%");
            user = query.getResultList().get(0);
            System.out.println("SearchedUser with Hibernate executed");
            transaction.commit();

        } catch (Exception e) {
            System.out.println("Couldn't get searched user with Hibernate");
            e.printStackTrace();
        }
        return user;
    }
}
