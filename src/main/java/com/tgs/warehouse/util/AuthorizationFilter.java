package com.tgs.warehouse.util;

import com.tgs.warehouse.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by dana on 4/20/2017.
 */
@WebFilter(urlPatterns = { "/warehouseOperations" }, filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURL().toString();
        String action = req.getParameter("action");

        System.out.println("In AuthorizationFilter for url: " + url + "?action=" + action);

        if(action != null && (action.equals("deletePallet") || action.equals("addPallet"))){
            HttpSession session = req.getSession(true);

            if (session.getAttribute("user") == null) {
                ((HttpServletResponse) response).sendRedirect("Login.jsp");
            }
            else {
                String userType = ((User) session.getAttribute("user")).getUserType();
                if (!userType.equals("A")) {
                    ((HttpServletResponse) response).sendRedirect("Login.jsp");
                }
                else
                {
                    chain.doFilter(request, response);
                }
            }

        } else {
            chain.doFilter(request, response);
        }

        /*if("login".equals(action) || url.endsWith("/Login.jsp")){
            chain.doFilter(request, response);
        } else {
            HttpSession session = req.getSession(true);
            if(session.getAttribute("user") != null){
                String userType = ((User) session.getAttribute("user")).getUserType();

                if (!userType.equals("A") && action != null) {
                    if (action.equals("deletePallet") || action.equals("addPallet")) {
                        ((HttpServletResponse) response).sendRedirect("Login.jsp");
                    }
                }
            }
            chain.doFilter(request, response);
        }*/
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
