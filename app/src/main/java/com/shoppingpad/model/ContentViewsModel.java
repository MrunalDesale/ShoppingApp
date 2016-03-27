package com.shoppingpad.model;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgelabz3 on 15/3/16.
 * Purpose:
 *
 * 1. This class contains all attributes of Content View
 * 2. It also populates data retrieved from JSON
 */
public class ContentViewsModel {

    public String mNoOfViews;
    public String mLastViewedDateTime;
    public String mDisplayProfile;
    public String mEmail;
    public String mAction;
    public String mLastName;
    public String mFirstName;
    public String mUserId;
    public String mContentId;
    public String mUserAdminId;
    public String mUserContentId;
    public String mNumberOfParticipant;

    public void populateDummyData(JSONObject serviceHandlerView){

        //Fetches data from json object and set to ContentView...
        try {
            mUserContentId = serviceHandlerView.getString("userContentId");
            mUserAdminId = serviceHandlerView.getString("userAdminId");
            mContentId = serviceHandlerView.getString("contentId");
            mUserId = serviceHandlerView.getString("userId");
            mFirstName = serviceHandlerView.getString("firstName");
            mLastName = serviceHandlerView.getString("lastName");
            mEmail = serviceHandlerView.getString("email");
            mDisplayProfile = serviceHandlerView.getString("displayProfile");
            mLastViewedDateTime = serviceHandlerView.getString("lastViewedDateTime");
            mNoOfViews = serviceHandlerView.getString("numberOfViews");
            mAction = serviceHandlerView.getString("action");
            mNumberOfParticipant = serviceHandlerView.getString("numberofparticipant");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
