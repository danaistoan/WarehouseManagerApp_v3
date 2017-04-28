package com.tgs.warehouse.controller;

/**
 * Created by dana on 4/25/2017.
 */
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tgs.warehouse.backingbean.UserBackingBean;
import com.tgs.warehouse.dao.LogisticUnitDAO;
import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.entities.User;
import com.tgs.warehouse.util.DataTablesParamUtil;
import com.tgs.warehouse.util.JQueryDataTableParamModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class WarehouseController {

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

        String username=request.getParameter("username");
        String password=request.getParameter("password");

        UserBackingBean userBackingBean = new UserBackingBean();
        User userLogat = userBackingBean.doLogin(username, password);
        ModelAndView mv;

        if(userLogat != null) {
            request.getSession().setAttribute("user", userLogat);
            System.out.println("Am pus user-ul pe sesiune");
            mv = new ModelAndView("showAllPallets");
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

    @RequestMapping(value = "showAllPallets", method = RequestMethod.GET)
    public ModelAndView showAllPallets() throws IOException {
        System.out.println("In showAllPallets MAV");
        ModelAndView mv = new ModelAndView("showAllPallets");
        return mv;
    }

    @RequestMapping(value = "getAllPallets", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAllPallets(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("In getAllPallets");

        JQueryDataTableParamModel param = DataTablesParamUtil.getParam(request);

        LogisticUnitDAO logisticUnit = new LogisticUnitDAO();
        List<ProductPallet> allPallets = logisticUnit.getAllPallets();

        if(param.searchValue != null && param.searchValue != ""){
            return searchPalletByDescriptionJson(param, allPallets, logisticUnit);
        } else {
            return getAllPalletsJson(param, allPallets);
        }
    }

    @RequestMapping(value = "addPallet", method = RequestMethod.POST)
    public ModelAndView addPallet(HttpServletRequest request){

        ObjectMapper mapper = new ObjectMapper();
        ProductPallet pallet = new ProductPallet();
        LogisticUnitDAO logisticUnit = new LogisticUnitDAO();

        try {
            String jsonPallet = request.getParameter("palletJson");
            pallet = mapper.readValue(jsonPallet, ProductPallet.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logisticUnit.insertProductPallet(pallet);
        System.out.println("New Pallet added with Spring Controller");
        ModelAndView mav = new ModelAndView("showAllPallets");
        return mav;
    }

    @RequestMapping(value = "deletePallet", method = RequestMethod.POST)
    public ModelAndView deletePallet(HttpServletRequest request){

        Long palletId = Long.parseLong(request.getParameter("palletId"));
        LogisticUnitDAO logisticUnit = new LogisticUnitDAO();
        boolean palletDeleted = logisticUnit.deleteProductPallet(palletId);

        ModelAndView mav = new ModelAndView("showAllPallets");

        if (palletDeleted) {
            System.out.println("The pallet with id " + palletId + " was deleted from the warehouse");
        } else {
            System.out.println("The pallet could not be deleted");
        }
        return mav;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request){

        request.getSession().removeAttribute("user");
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("greeting", "Welcome to Web Warehouse!");
        return mav;
    }

    private String searchPalletByDescriptionJson(JQueryDataTableParamModel param, List<ProductPallet> allPallets, LogisticUnitDAO logisticUnit) throws IOException {

        List<ProductPallet> foundPalletList = logisticUnit.search(param.searchValue);

        int foundPalletsSize = foundPalletList.size();
        int iTotalRecords = allPallets.size();
        int iTotalDisplayRecords = foundPalletsSize;
        List<ProductPallet> resultPallets = foundPalletList.subList(param.start, Math.min(param.start + param.length, foundPalletsSize));

        return getPalletToJson(iTotalRecords, iTotalDisplayRecords, resultPallets);
    }

    private String getAllPalletsJson(JQueryDataTableParamModel param, List<ProductPallet> allPallets) throws IOException {

        int allPalletsSize = allPallets.size();
        List<ProductPallet> resultPallets = allPallets.subList(param.start, Math.min(param.start + param.length, allPalletsSize));

        int iTotalRecords = allPalletsSize;
        int iTotalDisplayRecords = allPalletsSize;

        return getPalletToJson(iTotalRecords, iTotalDisplayRecords, resultPallets);
    }

    private String getPalletToJson(int iTotalRecords, int iTotalDisplayRecords, List<ProductPallet> resultPallets) {

        Map palletsDataTableMap = new HashMap();
        palletsDataTableMap.put("iTotalRecords", iTotalRecords);
        palletsDataTableMap.put("iTotalDisplayRecords", iTotalDisplayRecords);
        palletsDataTableMap.put("palletList", resultPallets);

        String palletJson = getObjectToJson(palletsDataTableMap);
        return palletJson;
    }

    private String getCurrentUserJson(HttpServletRequest request) {

        User userAuthenticated = ((User) request.getSession().getAttribute("user"));
        String userJson = getObjectToJson(userAuthenticated);
        return userJson;
    }

    private String getObjectToJson(Object object){

        String objectAsString = null;
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectAsString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectAsString;
    }
}