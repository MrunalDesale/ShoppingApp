package com.shoppingpad.servicehandler;

import org.json.JSONArray;

import java.util.Random;

/**
 * Created by bridgelabz3 on 13/3/16.
 * 1.This class fetches data from server and return that data to controller.
 * 2.It also return data to model.
 */
public class ContentListServiceHandler {

    public JSONArray mViewJsonArray;
    public JSONArray mInfoJsonArray;

    int code = 0;
    public JSONArray getmInfoJsonArray() {
        //Code to get json data of content info

        String infoUrl="http://54.86.64.100:3000/api/v4/content/info";

        JsonParser jsonParser1 = new JsonParser();
        mInfoJsonArray=jsonParser1.getJSONFromUrl(infoUrl);
        return mInfoJsonArray;
    }

    public JSONArray getmViewJsonArray() {
        //Code to get json data of content view
        String viewUrl="http://54.86.64.100:3000/api/v4/content/View";

        JsonParser jsonParser = new JsonParser();
        mViewJsonArray=jsonParser.getJSONFromUrl(viewUrl);

        return mViewJsonArray;
    }

    public int generateOtp(){
        //Generate 6 digits OTP
        Random ran=new Random();
        return (code= (100000 + ran.nextInt(900000)));
    }
}
