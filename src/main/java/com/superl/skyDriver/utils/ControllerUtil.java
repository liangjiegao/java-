package com.superl.skyDriver.utils;

import com.superl.skyDriver.responseEntry.JsonObjectResponse;

public class ControllerUtil {
    public static JsonObjectResponse packageJOR(String code, String message, Object object){
        JsonObjectResponse jsonObjectResponse = new JsonObjectResponse();
        jsonObjectResponse.setCode(code);
        jsonObjectResponse.setMessage(message);
        jsonObjectResponse.setObject(object);
        return jsonObjectResponse;
    }
}
