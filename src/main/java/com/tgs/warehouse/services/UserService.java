package com.tgs.warehouse.services;

import com.tgs.warehouse.dao.UserDAO;
import com.tgs.warehouse.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by dana on 4/19/2017.
 */

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        Objects.requireNonNull(userDAO);
        this.userDAO = userDAO;
    }

    public User doLogin(String username, String password){

        User user = userDAO.searchUser(username);

        if(user == null || !user.getPassword().equals(password)){
            System.out.println("User not found! Wrong username or password");
            return null;
        }
        return user;
    }
}
