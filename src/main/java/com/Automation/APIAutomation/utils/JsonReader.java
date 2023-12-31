package com.Automation.APIAutomation.utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

/**
 * @author psawale
 */
public class JsonReader {
    public static String getTestData(String key)  {
        String testDataValue;
        return testDataValue = (String) getJsonData().get(key);//input is the key
    }

    public static JSONObject getJsonData() {

        //pass the path of the testdata.json file
        File filename = new File("resources//TestData//testdata.json");
        //convert json file into string
        String json = null;
        try {
            json = FileUtils.readFileToString(filename, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //parse the string into object
        Object obj = null;
        try {
            obj = new JSONParser().parse(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //give jsonobject so that I can return it to the function everytime it get called
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }

    public static JSONArray getJsonArray(String key){
        JSONObject jsonObject = null;
        jsonObject = getJsonData();
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray;
    }

    public static Object getJsonArrayData(String key, int index) {
        JSONArray languages = getJsonArray(key);
        return languages.get(index);
    }
}
