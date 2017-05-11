package com.tgs.warehouse.controller;

/**
 * Created by dana on 4/25/2017.
 */
import com.tgs.warehouse.services.UserService;
import com.tgs.warehouse.entities.User;
import com.tgs.warehouse.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class WarehouseController {

    @Autowired
    UserService userService;

    public WarehouseController(){
        System.out.println("In WarehouseController");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("greeting", "Welcome to Web Warehouse!");
        System.out.println("In MAV Login");
        return mv;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("greeting", "Welcome to Web Warehouse!");
        System.out.println("In MAV Login");
        return mv;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView processLogin(HttpServletRequest request){

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User userLogat = userService.doLogin(username, password);
        ModelAndView mv;

        if(userLogat != null) {
            request.getSession().setAttribute("user", userLogat);
            System.out.println("Am pus user-ul pe sesiune");
            mv = new ModelAndView("welcome");
        }
        else {
            mv = new ModelAndView("login");
            mv.addObject("greeting", "Welcome to Web Warehouse!");
            mv.addObject("loginFailure", true);
        }
        System.out.println("In MAV ProcessLogin");
        return mv;
    }

    @RequestMapping(value = "userInfo", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getUserInfo(HttpServletRequest request) throws IOException {
        System.out.println("In getUserInfo");
        return getCurrentUserJson(request);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request){

        request.getSession().removeAttribute("user");
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("greeting", "Welcome to Web Warehouse!");
        return mav;
    }

    private String getCurrentUserJson(HttpServletRequest request) {

        User userAuthenticated = ((User) request.getSession().getAttribute("user"));
        String userJson = JsonUtil.getObjectToJson(userAuthenticated);
        return userJson;
    }
}