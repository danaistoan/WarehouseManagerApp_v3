package com.tgs.warehouse.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dana on 4/6/2017.
 */
public class DataTablesParamUtil {

    public static JQueryDataTableParamModel getParam(HttpServletRequest request)
    {
        JQueryDataTableParamModel param = new JQueryDataTableParamModel();
        param.start = Integer.parseInt(request.getParameter("start"));
        param.length = Integer.parseInt(request.getParameter("length"));
        param.searchValue = request.getParameter("search[value]");

        return param;
    }
}
