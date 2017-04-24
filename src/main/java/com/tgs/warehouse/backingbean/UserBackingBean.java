package com.tgs.warehouse.backingbean;

import com.tgs.warehouse.dao.UserDAO;
import com.tgs.warehouse.entities.User;

/**
 * Created by dana on 4/19/2017.
 */
public class UserBackingBean {

    UserDAO userDAO = new UserDAO();

    public User doLogin(String username, String password){

        User user = userDAO.searchUser(username);

        if(user == null || !user.getPassword().equals(password)){
            System.out.println("User not found! Wrong username or password");
            return null;
        }
        return user;
    }
}
