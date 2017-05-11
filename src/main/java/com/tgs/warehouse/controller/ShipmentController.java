package com.tgs.warehouse.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.entities.Shipment;
import com.tgs.warehouse.services.PalletService;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dana on 5/10/2017.
 */
@Controller
@RequestMapping("Shipments")
public class ShipmentController {

    @Autowired
    ShipmentService shipmentService;

    @Autowired
    PalletService palletService;

    @RequestMapping(value = "showShipments", method = RequestMethod.GET)
    public ModelAndView showShipments() throws IOException {
        System.out.println("In showShipments MAV");
        ModelAndView mv = new ModelAndView("showShipments");
        return mv;
    }

    @RequestMapping(value = "getAllShipments", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAllShipments(HttpServletRequest request) throws IOException {
        System.out.println("In getAllShipments");

        JQueryDataTableParamModel param = DataTablesParamUtil.getParam(request);

        List<Shipment> shipmentList = shipmentService.getAllShipments();

        if(param.searchValue != null && param.searchValue != ""){
            return searchShipmentsJson(param, shipmentList, shipmentService);
        } else {
            return getAllShipmentsJson(param, shipmentList);
        }
    }

    private String searchShipmentsJson(JQueryDataTableParamModel param, List<Shipment> allShipments, ShipmentService shipmentService) throws IOException {

        List<Shipment> foundShipmentList = shipmentService.searchShipments(param.searchValue);

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

        String shipmentJson = JsonUtil.getObjectToJson(shipmentsDataTableMap);
        return shipmentJson;
    }

    @RequestMapping(value = "deleteShipment", method = RequestMethod.POST)
    public ModelAndView deleteShipment(HttpServletRequest request){

        Long shipmentId = Long.parseLong(request.getParameter("shipmentId"));
        boolean shipmentDeleted = shipmentService.deleteShipment(shipmentId);
        ModelAndView mv = new ModelAndView("showShipments");

        if (shipmentDeleted) {
            System.out.println("The shipment with id " + shipmentId + " was deleted");
        } else {
            System.out.println("The shipment could not be deleted");
        }
        return mv;
    }

    @RequestMapping(value = "addShipment", method = RequestMethod.POST)
    public ModelAndView addShipment(HttpServletRequest request){

        Long plShipmentId = Long.parseLong(request.getParameter("plannedShipmentId"));
        ObjectMapper mapper = new ObjectMapper();
        ArrayList palletIdList = new ArrayList();
        try {
            String jsonIdArray = request.getParameter("palletIdListJson");
            palletIdList = mapper.readValue(jsonIdArray, ArrayList.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ProductPallet> palletList = new ArrayList<>();
        ProductPallet palletToBeShiped;

        for(Object ppId : palletIdList){
            Long palletId = Long.parseLong(ppId.toString());
            palletToBeShiped = palletService.getProductPalletById(palletId);
            palletList.add(palletToBeShiped);
        }

        shipmentService.saveShipment(plShipmentId, palletList);
        ModelAndView mv = new ModelAndView("showShipments");

        return mv;
    }

    @RequestMapping(value = "updateShipment", method = RequestMethod.POST)
    public ModelAndView updateShipment(HttpServletRequest request){

        Long shipmentId = Long.parseLong(request.getParameter("shipmentId"));
        Long plShipmentId = Long.parseLong(request.getParameter("plannedShipmentId"));
        ObjectMapper mapper = new ObjectMapper();
        ArrayList palletIdList = new ArrayList();
        try {
            String jsonIdArray = request.getParameter("palletIdListJson");
            palletIdList = mapper.readValue(jsonIdArray, ArrayList.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ProductPallet> palletList = new ArrayList<>();
        ProductPallet palletToBeShiped;

        for(Object ppId : palletIdList){
            Long palletId = Long.parseLong(ppId.toString());
            palletToBeShiped = palletService.getProductPalletById(palletId);
            palletList.add(palletToBeShiped);
        }

        shipmentService.updateShipment(shipmentId, plShipmentId, palletList);
        ModelAndView mv = new ModelAndView("showShipments");

        return mv;
    }
}
