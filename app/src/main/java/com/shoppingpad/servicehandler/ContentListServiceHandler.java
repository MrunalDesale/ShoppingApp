package com.shoppingpad.servicehandler;

import android.util.Log;

import org.json.JSONArray;
/**
 * Created by bridgelabz3 on 13/3/16.
 * 1.This class fetches data from server and return that data to controller.
 * 2.It also return data to model.
 */
public class ContentListServiceHandler {

    public JSONArray mViewJsonArray;
    public JSONArray mInfoJsonArray;

    public JSONArray getmInfoJsonArray() {
        //Code to get json data of content info
        String infoUrl="http://54.86.64.100:3000/api/v1/content/content-info";

        JsonParser jsonParser1 = new JsonParser();
        mInfoJsonArray=jsonParser1.getJSONFromUrl(infoUrl);
        return mInfoJsonArray;
    }

    public JSONArray getmViewJsonArray() {
        //Code to get json data of content view
        String viewUrl="http://54.86.64.100:3000/api/v1/content/user-content-view";

        JsonParser jsonParser = new JsonParser();
        mViewJsonArray=jsonParser.getJSONFromUrl(viewUrl);

        return mViewJsonArray;
    }
}
