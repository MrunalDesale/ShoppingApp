package com.shoppingpad.controller;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.dbhandler.ContentListLocalDB;
import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ContentViewsModel;
import com.shoppingpad.servicehandler.ContentListServiceHandler;
import com.shoppingpad.servicehandler.RegistrationServiceHandler;
import com.shoppingpad.viewmodel.ContentViewModelData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bridgelabz3 on 18/3/16.
 * Purpose:
 *
 * 1. This class acts as manager between all classes.
 * 2. It manages data flow between ViewModel and Model, ViewModel and rest or
 * database, model and rest or database.
 * 3. This class is also responsible for adding view and info data into database.
 * 4. It also generate otp and send it to view model.
 * 5. It passes username and mobile number to server for verification and receive
 * response from server and send back that response to user.
 */
public class ContentListController {

    public static final boolean IS_UNIT_TEST = false;
    int code=0;
    String mFileName;
    Bitmap mBitmap;

    public ContentListServiceHandler mContentListServiceHandler;
    RegistrationServiceHandler mRegistrationServiceHandler;

    public List<ContentInfoModel> mContentInfoModelList;
    public List<ContentViewsModel> mContentViewsModelList;
    public List <ContentViewModelData> mContentViewModelDataList;

    ContentListLocalDB mLocalDB;

    public ContentListController(Context context){

        if(IS_UNIT_TEST){

            //Calling dummy method if rest call is not present...
            mContentViewModelDataList=populateDummyData();
        }
        else {

            //Initialize list...
            mContentInfoModelList=new ArrayList<>();
            mContentViewsModelList=new ArrayList<>();

            //Call to database...
            mLocalDB=new ContentListLocalDB(context);
            mContentListServiceHandler=new ContentListServiceHandler();
        }
    }

    public ContentListController() {
    }

    //This method pass username and mob number to Service handler and get response
    // from server
    public String setUserInfo(String phno,String name,String encodedImage){
        String response=null;
        try {

            //Call post method to server...
            mRegistrationServiceHandler=new RegistrationServiceHandler();
            response= mRegistrationServiceHandler.postData(phno, name,encodedImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    //This method generate OTP...
    public int generateOtp(){
        //Generate 6 digits OTP using random function...
        Random ran=new Random();
        return code= (100000 + ran.nextInt(900000));
    }

    //Return generated OTP...
    public int getOtp(){
        return generateOtp();
    }

    //Method to get list of ContentInfo and ContentView from json...
    public List<ContentInfoModel> getInfoModelList(){
        return mContentInfoModelList=getContentInfoJson();
    }
    public List<ContentViewsModel> getContentViewsModelList(){
        return mContentViewsModelList=getContentViewsJson();
    }

    //Method to get Content info json data from service handler....
    public List<ContentInfoModel> getContentInfoJson(){
        List <ContentInfoModel> contentInfoModelList=new ArrayList<>();
        try {
        JSONArray infoJsonArray=mContentListServiceHandler.getmInfoJsonArray();

        //Get service handlers Info list that contains Info data retrieved from Json
        for (int i=0;i<infoJsonArray.length();i++){
                //Converts json data to json object and add to list
                //It also populate info model data...
                ContentInfoModel contentInfoModel = new ContentInfoModel();
                JSONObject jsonObject = infoJsonArray.getJSONObject(i);
                contentInfoModel.populateInfoDummyData(jsonObject);
                contentInfoModelList.add(contentInfoModel);
            }
        }
        catch (NullPointerException | JSONException e){
            e.printStackTrace();
        }

        //This loop insert info model list data into database one by one...
        for(int i=0 ;i<contentInfoModelList.size();i++){
            mLocalDB.insertInfoContentData(contentInfoModelList.get(i));
        }
        return contentInfoModelList;
    }

    //Method to get Content view json data from service handler....
    public List<ContentViewsModel> getContentViewsJson(){

        List<ContentViewsModel> contentViewsModelList=new ArrayList<>();

        try {
        //Get service handlers View list that contains View data retrieved from Json
        JSONArray viewJsonArray = mContentListServiceHandler.getmViewJsonArray();
        for (int i=0;i<viewJsonArray.length();i++){

                //Converts json data to json object and add to list
                //It also populate view model data...
                JSONObject jsonObject = viewJsonArray.getJSONObject(i);
                ContentViewsModel contentViewsModel=new ContentViewsModel();
                contentViewsModel.populateViewDummyData(jsonObject);
                contentViewsModelList.add(contentViewsModel);
            }
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
        }

        //This loop insert view model list data into database one by one...
        for(int i=0;i<contentViewsModelList.size();i++){
            mLocalDB.insertViewContentData(contentViewsModelList.get(i));
        }
        return contentViewsModelList;
    }

    //Method to populate dummy data without rest call....
    public List<ContentViewModelData> populateDummyData(){
        List <ContentViewModelData> contentViewModelDataList = new ArrayList<>();

        for (int i=0;i<5;i++) {
            ContentViewModelData contentViewModelData = new ContentViewModelData();

            //Set dummy data to contents of view model data...
            contentViewModelData.mPartTitle = "dummy part time";
            contentViewModelData.mTimeTitle = "dummy time";
            contentViewModelData.mMainTitle = "dummy Main title";
            contentViewModelData.mStatusTitle = "dummy status title";
            contentViewModelData.mViewTitle = "dummy View title";

            contentViewModelData.mShareIcon = R.drawable.share;
            contentViewModelDataList.add(contentViewModelData);
        }
        return contentViewModelDataList;
    }

    //Get info data from database...
    public List<ContentInfoModel> getInfoDatabase(){

        Cursor infoCursor=mLocalDB.getContentInfoData();
        JSONArray infoJsonArray = cursorToJson(infoCursor);
        List <ContentInfoModel> contentInfoModelList=new ArrayList<>();
        for (int i=0;i<infoJsonArray.length();i++){
            try {
                //Converts json data to json object and add to list
                //It also populate view model data...
                JSONObject jsonObject = infoJsonArray.getJSONObject(i);
                ContentInfoModel contentInfoModel = new ContentInfoModel();
                contentInfoModel.populateInfoDummyData(jsonObject);
                contentInfoModelList.add(contentInfoModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return contentInfoModelList;
    }

    //Get view data from database...
    public List <ContentViewsModel> getViewDatabase(){
        Cursor viewCursor=mLocalDB.getContentViewData();
        JSONArray viewJsonArray=cursorToJson(viewCursor);
        List <ContentViewsModel> contentViewsModelList=new ArrayList<>();
        for (int i=0;i<viewJsonArray.length();i++){
            try {

                //Converts json data to json object and add to list
                //It also populate view model data...
                JSONObject jsonObject = viewJsonArray.getJSONObject(i);
                ContentViewsModel contentViewsModel = new ContentViewsModel();
                contentViewsModel.populateViewDummyData(jsonObject);
                contentViewsModelList.add(contentViewsModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return contentViewsModelList;
    }

    //This method converts cursor data to json format...
    public JSONArray cursorToJson(Cursor cursor){

        JSONArray result = new JSONArray();

        //Move cursor to first record...
        cursor.moveToFirst();

        //isAfterLast() returns whether cursor is pointing to position after last row
        while (!cursor.isAfterLast()) {

            //Get number of columns in cursor...
            int columnCount = cursor.getColumnCount();
            JSONObject rowObj = new JSONObject();

            for (int i = 0; i < columnCount; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        //Put data from cursor into json obj in key-value format...
                        rowObj.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //Add json obj int json array...
            result.put(rowObj);
            cursor.moveToNext();
        }
        cursor.close();
        //return resultant json array...
        return result;
    }
}