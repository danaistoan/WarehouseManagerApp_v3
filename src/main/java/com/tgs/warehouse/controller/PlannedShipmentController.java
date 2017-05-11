package com.tgs.warehouse.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgs.warehouse.entities.PlannedShipment;
import com.tgs.warehouse.services.PlannedShipmentService;
import com.tgs.warehouse.services.ShipmentService;
import com.tgs.warehouse.util.DataTablesParamUtil;
import com.tgs.warehouse.util.JQueryDataTableParamModel;
import com.tgs.warehouse.util.JsonUtil;
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

/**
 * Created by dana on 5/10/2017.
 */
@Controller
@RequestMapping("PlannedShipments")
public class PlannedShipmentController {


    @Autowired
    PlannedShipmentService plannedShipmentService;

    @Autowired
    ShipmentService shipmentService;

    @RequestMapping(value = "showPlannedShipments", method = RequestMethod.GET)
    public ModelAndView showPlannedShipments() throws IOException {
        System.out.println("In showShipments MAV");
        ModelAndView mv = new ModelAndView("showPlannedShipments");
        return mv;
    }

    @RequestMapping(value = "getAllPlannedShipments", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAllPlannedShipments(HttpServletRequest request) throws IOException {
        System.out.println("In getAllPlannedShipments");

        JQueryDataTableParamModel param = DataTablesParamUtil.getParam(request);

        List<PlannedShipment> plannedShipments = plannedShipmentService.getAllPlannedShipments();

        if(param.searchValue != null && param.searchValue != ""){
            return searchPlannedShipmentsJson(param, plannedShipments, shipmentService);
        } else {
            return getAllPlShipmentsJson(param, plannedShipments);
        }
    }

    // Get planned shipments for shipment dialog-form
    @RequestMapping(value = "getPlannedShipments", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPlannedShipments() throws IOException {
        System.out.println("In getPlannedShipments");

        List<PlannedShipment> plannedShipments = plannedShipmentService.getPlannedShipmentsWithoutShipment();

        return getAllPlShipmentsJson(null, plannedShipments);
    }

    private String searchPlannedShipmentsJson(JQueryDataTableParamModel param, List<PlannedShipment> allShipments, ShipmentService shipmentService) throws IOException {

        List<PlannedShipment> foundPlannedShipmentList = plannedShipmentService.searchPlannedShipments(param.searchValue);

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

        String plShipmentJson = JsonUtil.getObjectToJson(plShipmentsDataTableMap);
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
        plannedShipmentService.savePlannedShipment(plannedShipment);
        System.out.println("New Planned Shipment added with Spring Controller");
        ModelAndView mv = new ModelAndView("showPlannedShipments");
        return mv;
    }

    @RequestMapping(value = "deletePlannedShipment", method = RequestMethod.POST)
    public ModelAndView deletePlannedShipment(HttpServletRequest request){

        Long plShipmentId = Long.parseLong(request.getParameter("plShipmentId"));
        boolean plShipmentDeleted = plannedShipmentService.deletePlannedShipment(plShipmentId);
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
        plannedShipmentService.savePlannedShipment(plannedShipment);
        System.out.println("Planned Shipment updated with Spring Controller");
        ModelAndView mv = new ModelAndView("showPlannedShipments");
        return mv;
    }
}
