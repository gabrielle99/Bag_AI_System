package com.tianci.common.constants;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AppConstants {
    // Application Name
    public static String APP_NAME = "TianCi-AI";

    public  final static String CHARTER_NAME = "UTF-8";

    public static String PROFILE_NAME = "";


    public static boolean isProd(){
        return "prod".equalsIgnoreCase(PROFILE_NAME);
    }

    public static ObjectMapper objectMapper = null;
}
