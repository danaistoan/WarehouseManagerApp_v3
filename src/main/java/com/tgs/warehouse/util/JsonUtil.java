package com.tgs.warehouse.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * Created by dana on 5/10/2017.
 */
public class JsonUtil {

    public static String getObjectToJson(Object object){

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
