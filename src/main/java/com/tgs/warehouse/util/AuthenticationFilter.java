package com.tgs.warehouse.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dana on 4/18/2017.
 */
@WebFilter(urlPatterns = { "/*" }, filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURL().toString();
        String action = httpRequest.getParameter("action");

        System.out.println("In AuthenticationFilter for url: " + url + "?action=" + action);

        if (httpRequest.getSession().getAttribute("user") == null) {
            if (url.endsWith("/Login.jsp") ||
                    (url.endsWith(".css")) ||
                    (url.endsWith("/warehouseOperations") && ("login".equals(action))))
            {
                // Do nothing
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendRedirect("Login.jsp");
            }
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
