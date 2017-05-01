package com.tgs.warehouse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.tgs.warehouse.entities.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by dana on 4/18/2017.
 */
public class UserDAO {

    private SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public User searchUser(String username) {

        Session session = sessionFactory.getCurrentSession();
        String hql = "select u from User u where lower(u.username) like lower(:username) ";
        TypedQuery<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", "%" + username + "%");

        List<User> userList = query.getResultList();
        if(userList.size() != 0) {
            return userList.get(0);
        }
        return null;
    }
}
