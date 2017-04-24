package com.tgs.warehouse.util;

/**
 * Created by dana on 4/6/2017.
 */
public class JQueryDataTableParamModel {


    // Request sequence number sent by DataTable, same value must be returned in response
    //public String echo;

    // Number of records that should be shown in table
    public int length;

    // First record that should be shown(used for paging)
    public int start;

    // Text used for filtering
    public String searchValue;
}
