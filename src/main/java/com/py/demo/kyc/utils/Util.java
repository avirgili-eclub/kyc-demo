package com.py.demo.kyc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    static final Logger log = LoggerFactory.getLogger(Util.class);

    public static JSONObject getJsonFromObject(Object object) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            var jsonCharge = JSONValue.parse(mapper.writeValueAsString(object));
            return (JSONObject) jsonCharge;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static JSONObject getJsonFromString(String stringObject) throws Exception {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(stringObject);
        } catch (ParseException e) {
            throw new Exception(e.getMessage());
        }
    }

    public static Object convertStringJsonToObject(String json, Class type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JSR310Module());
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, type);
        } catch (IllegalArgumentException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

