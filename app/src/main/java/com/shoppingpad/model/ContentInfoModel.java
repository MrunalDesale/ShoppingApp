package com.shoppingpad.model;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bridgelabz3 on 15/3/16.
 * Purpose:
 * 1. This class contains all attributes of ContentInfo
 * 2. It also populates data retrieved from JSON
 */
public class ContentInfoModel {

    public String mModifiedAt;
    public String mCreatedAt;
    public String mSynchDateTime;
    public String mDescription;
    public String mContentLink;
    public String mImagesLink;
    public String mDisplayName;
    public String mUrl;
    public String mTitle;
    public String mContentType;
    public String mContentId;

    public void populateInfoDummyData(JSONObject contentInfoModelList){

        if(contentInfoModelList != null) {
            //Fetches data from json object and set to ContentInfo...
            try {
                mContentType = contentInfoModelList.getString("contentType");
                mTitle = String.valueOf(contentInfoModelList.getInt("title"));
                mUrl = contentInfoModelList.getString("url");
                mDisplayName = contentInfoModelList.getString("display_name");
                mImagesLink = contentInfoModelList.getString("imagesLink");
                mContentLink = contentInfoModelList.getString("contentLink");
                mDescription = contentInfoModelList.getString("decription");
                mSynchDateTime = contentInfoModelList.getString("syncDateTime");
                mCreatedAt = contentInfoModelList.getString("created_at");
                mModifiedAt = contentInfoModelList.getString("modified_at");
                mContentId = Integer.toString(contentInfoModelList.getInt("content_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
