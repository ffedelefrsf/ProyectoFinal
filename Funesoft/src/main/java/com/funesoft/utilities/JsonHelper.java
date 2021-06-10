/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.funesoft.utilities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author faust
 */
public class JsonHelper {
    
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    
    public JsonHelper() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        JSON_MAPPER.setDateFormat(df);
        JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JSON_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        JSON_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }
    
    public static String objectToString(Object object) throws JsonProcessingException {
        return JSON_MAPPER.writeValueAsString(object);
    }
    
    public static Object stringToObject(String sConsulta, Class cClaseASerializar) throws IOException{
        return JSON_MAPPER.readValue(sConsulta, cClaseASerializar);
    }
}
