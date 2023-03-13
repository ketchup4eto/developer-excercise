package com.exercise.cloudruid.utils.helper.classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Helpers {
    public static List<String> parseJsonList(String itemNames) throws ParseException {
        JSONParser parser = new JSONParser(itemNames);
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> convertedList = new ArrayList<>();
        for (Object o : parser.list()) {
            Map map = objectMapper.convertValue(o, Map.class);
            convertedList.add(String.valueOf(map.get("name")));
        }
        return convertedList;
    }
}
