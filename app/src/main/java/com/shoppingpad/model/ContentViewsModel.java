package com.shoppingpad.model;

import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void populateViewDummyData(JSONObject serviceHandlerView) {

        if (serviceHandlerView != null) {
            //Fetches data from json object and set to ContentView...
            try {
                mUserContentId = Integer.toString(serviceHandlerView.getInt("userContentId"));
                mUserAdminId = Integer.toString(serviceHandlerView.optInt("userAdminId"));
                mContentId = Integer.toString(serviceHandlerView.getInt("contentId"));
                mUserId = Integer.toString(serviceHandlerView.getInt("userId"));
                mFirstName = serviceHandlerView.getString("firstName");
                mLastName = serviceHandlerView.getString("lastName");
                mEmail = serviceHandlerView.getString("email");
                mDisplayProfile = serviceHandlerView.getString("displayProfile");
                mLastViewedDateTime = serviceHandlerView.getString("lastViewedDateTime");
                mNoOfViews = Integer.toString(serviceHandlerView.getInt("numberOfViews"));
                mAction = serviceHandlerView.getString("action");
                mNumberOfParticipant = Integer.toString(serviceHandlerView.getInt("numberofparticipant"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
