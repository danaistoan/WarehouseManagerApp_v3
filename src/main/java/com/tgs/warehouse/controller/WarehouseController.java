package com.tgs.warehouse.controller;

/**
 * Created by dana on 4/25/2017.
 */
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tgs.warehouse.dao.ShipmentDAO;
import com.tgs.warehouse.entities.PlannedShipment;
import com.tgs.warehouse.entities.Shipment;
import com.tgs.warehouse.services.UserService;
import com.tgs.warehouse.dao.LogisticUnitDAO;
import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.entities.User;
import com.tgs.warehouse.util.DataTablesParamUtil;
import com.tgs.warehouse.util.JQueryDataTableParamModel;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LogisticUnitDAO logisticUnit;

    @Autowired
    private ShipmentDAO shipmentDAO;

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

    /*
    @RequestMapping(value = "getPallets", method = RequestMethod.GET)
    public ModelAndView navigateToPallets(){
        ModelAndView mv = new ModelAndView("showAllPallets");
        System.out.println("Navigating to Pallets");
        return mv;
    }

    @RequestMapping(value = "getShipments", method = RequestMethod.GET)
    public ModelAndView navigateToShipments(){
        ModelAndView mv = new ModelAndView("showShipments");
        System.out.println("Navigating to Shipments");
        return mv;
    }
    */

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

    @RequestMapping(value = "showAllPallets", method = RequestMethod.GET)
    public ModelAndView showAllPallets() throws IOException {
        System.out.println("In showAllPallets MAV");
        ModelAndView mv = new ModelAndView("showAllPallets");
        return mv;
    }

    //Get pallets for DT
    @RequestMapping(value = "getAllPallets", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAllPallets(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("In getAllPallets");

        JQueryDataTableParamModel param = DataTablesParamUtil.getParam(request);

        List<ProductPallet> allPallets = logisticUnit.getAllPallets();

        if(param.searchValue != null && param.searchValue != ""){
            return searchPalletByDescriptionJson(param, allPallets, logisticUnit);
        } else {
            return getAllPalletsJson(param, allPallets);
        }
    }

    //Get pallets for shipment dialog-form
    @RequestMapping(value = "getPallets", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPallets() throws IOException{
        System.out.println("In getPallets for Shipment creation");

        List<ProductPallet> productPalletList = logisticUnit.getAllPallets();

        return getAllPalletsJson(null, productPalletList);
    }

    @RequestMapping(value = "addPallet", method = RequestMethod.POST)
    public ModelAndView addPallet(HttpServletRequest request){

        ObjectMapper mapper = new ObjectMapper();
        ProductPallet pallet = new ProductPallet();

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
        List<ProductPallet> resultPallets;

        int iTotalRecords = allPalletsSize;
        int iTotalDisplayRecords = allPalletsSize;

        if (param != null)
            resultPallets = allPallets.subList(param.start, Math.min(param.start + param.length, allPalletsSize));
        else
            resultPallets = allPallets;

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

    @RequestMapping(value = "showPlannedShipments", method = RequestMethod.GET)
    public ModelAndView showPlannedShipments() throws IOException {
        System.out.println("In showShipments MAV");
        ModelAndView mv = new ModelAndView("showPlannedShipments");
        return mv;
    }

    @RequestMapping(value = "getAllPlannedShipments", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAllPlannedShipments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("In getAllPlannedShipments");

        JQueryDataTableParamModel param = DataTablesParamUtil.getParam(request);

        List<PlannedShipment> plannedShipments = shipmentDAO.getAllPlannedShipments();

        if(param.searchValue != null && param.searchValue != ""){
            return searchPlannedShipmentsJson(param, plannedShipments, shipmentDAO);
        } else {
            return getAllPlShipmentsJson(param, plannedShipments);
        }
    }

    @RequestMapping(value = "getPlannedShipments", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlannedShipments() throws IOException {
        System.out.println("In getPlannedShipments");

        List<PlannedShipment> plannedShipments = shipmentDAO.getAllPlannedShipments();

        return getAllPlShipmentsJson(null, plannedShipments);
    }

    private String searchPlannedShipmentsJson(JQueryDataTableParamModel param, List<PlannedShipment> allShipments, ShipmentDAO shipmentDAO) throws IOException {

        List<PlannedShipment> foundPlannedShipmentList = shipmentDAO.searchPlannedShipments(param.searchValue);

        int foundShipmentsSize = foundPlannedShipmentList.size();
        int iTotalRecords = allShipments.size();
        int iTotalDisplayRecords = foundShipmentsSize;
        List<PlannedShipment> resultPlannedShipment = foundPlannedShipmentList.subList(param.start, Math.min(param.start + param.length, foundShipmentsSize));

        return getPlShipmentToJson(iTotalRecords, iTotalDisplayRecords, resultPlannedShipment);
    }

    private String getAllPlShipmentsJson(JQueryDataTableParamModel param, List<PlannedShipment> allPlannedShipments) throws IOException {

        int allPlShipmentsSize = allPlannedShipments.size();
        List<PlannedShipment> resultPlShipments;

        if (param != null)
            resultPlShipments = allPlannedShipments.subList(param.start, Math.min(param.start + param.length, allPlShipmentsSize));
        else
            resultPlShipments = allPlannedShipments;

        int iTotalRecords = allPlShipmentsSize;
        int iTotalDisplayRecords = allPlShipmentsSize;

        return getPlShipmentToJson(iTotalRecords, iTotalDisplayRecords, resultPlShipments);
    }

    private String getPlShipmentToJson(int iTotalRecords, int iTotalDisplayRecords, List<PlannedShipment> plannedShipmentList) {

        Map plShipmentsDataTableMap = new HashMap();
        plShipmentsDataTableMap.put("iTotalRecords", iTotalRecords);
        plShipmentsDataTableMap.put("iTotalDisplayRecords", iTotalDisplayRecords);
        plShipmentsDataTableMap.put("plannedShipmentList", plannedShipmentList);

        String plShipmentJson = getObjectToJson(plShipmentsDataTableMap);
        return plShipmentJson;
    }

    @RequestMapping(value = "addPlannedShipment", method = RequestMethod.POST)
    public ModelAndView addPlannedShipment(HttpServletRequest request){

        ObjectMapper mapper = new ObjectMapper();
        PlannedShipment plannedShipment = new PlannedShipment();

        try {
            String jsonPlShipment = request.getParameter("plannedShipmentJson");
            plannedShipment = mapper.readValue(jsonPlShipment, PlannedShipment.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        shipmentDAO.insertPlannedShipment(plannedShipment);
        System.out.println("New Planned Shipment added with Spring Controller");
        ModelAndView mv = new ModelAndView("showPlannedShipments");
        return mv;
    }

    @RequestMapping(value = "deletePlannedShipment", method = RequestMethod.POST)
    public ModelAndView deletePlannedShipment(HttpServletRequest request){

        Long plShipmentId = Long.parseLong(request.getParameter("plShipmentId"));
        boolean plShipmentDeleted = shipmentDAO.deletePlannedShipment(plShipmentId);
        ModelAndView mv = new ModelAndView("showPlannedShipments");

        if (plShipmentDeleted) {
            System.out.println("The shipment with id " + plShipmentId + " was deleted");
        } else {
            System.out.println("The shipment could not be deleted");
        }
        return mv;
    }

    @RequestMapping(value = "updatePlannedShipment", method = RequestMethod.POST)
    public ModelAndView updatePlannedShipment(HttpServletRequest request){

        ObjectMapper mapper = new ObjectMapper();
        PlannedShipment plannedShipment = new PlannedShipment();

        try {
            String jsonPlShipment = request.getParameter("plannedShipmentJson");
            plannedShipment = mapper.readValue(jsonPlShipment, PlannedShipment.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        shipmentDAO.insertPlannedShipment(plannedShipment);
        System.out.println("Planned Shipment updated with Spring Controller");
        ModelAndView mv = new ModelAndView("showPlannedShipments");
        return mv;
    }

    @RequestMapping(value = "showShipments", method = RequestMethod.GET)
    public ModelAndView showShipments() throws IOException {
        System.out.println("In showShipments MAV");
        ModelAndView mv = new ModelAndView("showShipments");
        return mv;
    }

    @RequestMapping(value = "getAllShipments", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAllShipments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("In getAllShipments");

        JQueryDataTableParamModel param = DataTablesParamUtil.getParam(request);

        List<Shipment> shipmentList = shipmentDAO.getAllShipments();

        if(param.searchValue != null && param.searchValue != ""){
            return searchShipmentsJson(param, shipmentList, shipmentDAO);
        } else {
            return getAllShipmentsJson(param, shipmentList);
        }
    }

    private String searchShipmentsJson(JQueryDataTableParamModel param, List<Shipment> allShipments, ShipmentDAO shipmentDAO) throws IOException {

        List<Shipment> foundShipmentList = shipmentDAO.searchShipments(param.searchValue);

        int foundShipmentsSize = foundShipmentList.size();
        int iTotalRecords = allShipments.size();
        int iTotalDisplayRecords = foundShipmentsSize;
        List<Shipment> resultShipments = foundShipmentList.subList(param.start, Math.min(param.start + param.length, foundShipmentsSize));

        return getShipmentToJson(iTotalRecords, iTotalDisplayRecords, resultShipments);
    }

    private String getAllShipmentsJson(JQueryDataTableParamModel param, List<Shipment> allShipments) throws IOException {

        int allShipmentsSize = allShipments.size();
        List<Shipment> resultShipments = allShipments.subList(param.start, Math.min(param.start + param.length, allShipmentsSize));

        int iTotalRecords = allShipmentsSize;
        int iTotalDisplayRecords = allShipmentsSize;

        return getShipmentToJson(iTotalRecords, iTotalDisplayRecords, resultShipments);
    }

    private String getShipmentToJson(int iTotalRecords, int iTotalDisplayRecords, List<Shipment> shipmentList) {

        Map shipmentsDataTableMap = new HashMap();
        shipmentsDataTableMap.put("iTotalRecords", iTotalRecords);
        shipmentsDataTableMap.put("iTotalDisplayRecords", iTotalDisplayRecords);
        shipmentsDataTableMap.put("shipmentList", shipmentList);

        String shipmentJson = getObjectToJson(shipmentsDataTableMap);
        return shipmentJson;
    }

    @RequestMapping(value = "addShipment", method = RequestMethod.POST)
    public ModelAndView addShipment(HttpServletRequest request){

        ObjectMapper mapper = new ObjectMapper();
        Shipment shipment = new Shipment();

        try {
            String jsonShipment = request.getParameter("shipmentJson");
            shipment = mapper.readValue(jsonShipment, Shipment.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        shipmentDAO.insertShipment(shipment);
        System.out.println("New Shipment added with Spring Controller");
        ModelAndView mv = new ModelAndView("showShipments");
        return mv;
    }

    @RequestMapping(value = "deleteShipment", method = RequestMethod.POST)
    public ModelAndView deleteShipment(HttpServletRequest request){

        Long shipmentId = Long.parseLong(request.getParameter("shipmentId"));
        boolean shipmentDeleted = shipmentDAO.deleteShipment(shipmentId);
        ModelAndView mv = new ModelAndView("showShipments");

        if (shipmentDeleted) {
            System.out.println("The shipment with id " + shipmentId + " was deleted");
        } else {
            System.out.println("The shipment could not be deleted");
        }
        return mv;
    }

}