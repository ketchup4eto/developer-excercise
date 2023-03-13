package com.exercise.cloudruid.utils.helper.classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Helpers {
    /**
     * This method is used to parse a JSON array with item names to a regular String list.
     * @param itemNames this is the raw JSON array
     * @return a list of Strings that are ready to used
     * @throws ParseException when the JSON is not formatted properly and cannot be parsed
     */
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
