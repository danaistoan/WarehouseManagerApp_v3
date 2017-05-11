package com.tgs.warehouse.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.services.PalletService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dana on 5/10/2017.
 */
@Controller
@RequestMapping("Pallets")
public class PalletController {

    @Autowired
    PalletService palletService;

    @RequestMapping(value = "showAllPallets", method = RequestMethod.GET)
    public ModelAndView showAllPallets() throws IOException {
        System.out.println("In showAllPallets MAV");
        ModelAndView mv = new ModelAndView("showAllPallets");
        return mv;
    }

    //Get pallets for DT
    @RequestMapping(value = "getAllPallets", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAllPallets(HttpServletRequest request) throws IOException {
        System.out.println("In getAllPallets");

        JQueryDataTableParamModel param = DataTablesParamUtil.getParam(request);

        List<ProductPallet> allPallets = palletService.getAllPallets();

        if(param.searchValue != null && param.searchValue != ""){
            return searchPalletByDescriptionJson(param, allPallets, palletService);
        } else {
            return getAllPalletsJson(param, allPallets);
        }
    }

    //Get pallets for shipment dialog-form
    @RequestMapping(value = "getPallets", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPallets() throws IOException{
        System.out.println("In getPallets for Shipment creation");

        List<ProductPallet> productPalletList = palletService.getPalletsWithoutShipment();

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
        palletService.saveProductPallet(pallet);
        System.out.println("New Pallet added with Spring Controller");
        ModelAndView mav = new ModelAndView("showAllPallets");
        return mav;
    }

    @RequestMapping(value = "deletePallet", method = RequestMethod.POST)
    public ModelAndView deletePallet(HttpServletRequest request){

        Long palletId = Long.parseLong(request.getParameter("palletId"));
        boolean palletDeleted = palletService.deleteProductPallet(palletId);
        ModelAndView mav = new ModelAndView("showAllPallets");

        if (palletDeleted) {
            System.out.println("The pallet with id " + palletId + " was deleted from the warehouse");
        } else {
            System.out.println("The pallet could not be deleted");
        }
        return mav;
    }

    private String searchPalletByDescriptionJson(JQueryDataTableParamModel param, List<ProductPallet> allPallets, PalletService palletService) throws IOException {

        List<ProductPallet> foundPalletList = palletService.search(param.searchValue);

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

        String palletJson = JsonUtil.getObjectToJson(palletsDataTableMap);
        return palletJson;
    }
}
