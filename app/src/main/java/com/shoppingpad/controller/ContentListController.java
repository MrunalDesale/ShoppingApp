package com.shoppingpad.controller;

import android.content.Context;

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
 */
public class ContentListController {

    public static final boolean IS_UNIT_TEST = false;
    int code=0;
    boolean result;


    public ContentListServiceHandler mContentListServiceHandler;
    RegistrationServiceHandler mRegistrationServiceHandler;

    public List<ContentInfoModel> mContentInfoModelList;
    public List<ContentViewsModel> mContentViewsModelList;
    public List <ContentViewModelData> mContentViewModelDataList;

    public List <ContentInfoModel> contentInfoModelList;
    ContentListLocalDB mLocalDB;

    public ContentListController(Context context){

        if(IS_UNIT_TEST){
            //Calling dummy method if rest call is not present...

            mContentViewModelDataList=populateDummyData();
        }
        else {
            mContentInfoModelList=new ArrayList<>();
            mContentViewsModelList=new ArrayList<>();

            //Call to database...
            mLocalDB=new ContentListLocalDB(context);
            mContentListServiceHandler=new ContentListServiceHandler();

            //Call to populate database...
            populateDatabase();
        }
    }

    public ContentListController() {

    }

    public String setUserInfo(String phno,String name){
        String response=null;
        try {
            mRegistrationServiceHandler=new RegistrationServiceHandler();
            response=mRegistrationServiceHandler.postData(phno,name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    public int generateOtp(){
        //Generate 6 digits OTP
        int otp=0;
        Random ran=new Random();
        otp=code= (100000 + ran.nextInt(900000));
        return otp;
    }

    //Return generated OTP...
    public int getOtp(){
        return generateOtp();
    }

    //Method to get list of ContentInfo and ContentView from json...
    public List<ContentInfoModel> getInfoModelList(){
        try {
            mContentInfoModelList=getContentInfoJson();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return mContentInfoModelList;
    }
    public List<ContentViewsModel> getContentViewsModelList(){
        try{
            mContentViewsModelList=getContentViewsJson();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return mContentViewsModelList;
    }

    //Method to get Content info json data from service handler....
    public List<ContentInfoModel> getContentInfoJson(){

        try {
        contentInfoModelList=new ArrayList<>();
        JSONArray infoJsonArray=mContentListServiceHandler.getmInfoJsonArray();

        //Get service handlers Info list that contains Info data retrieved from Json
        for (int i=0;i<infoJsonArray.length();i++){

                ContentInfoModel contentInfoModel = new ContentInfoModel();
                JSONObject jsonObject = infoJsonArray.getJSONObject(i);
                contentInfoModel.populateDummyData(jsonObject);
                contentInfoModelList.add(contentInfoModel);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return contentInfoModelList;
    }

    //Method to get Content view json data from service handler....
    public List<ContentViewsModel> getContentViewsJson(){

        List<ContentViewsModel> contentViewsModelList=new ArrayList<>();

        //Get service handlers View list that contains View data retrieved from Json
        JSONArray viewJsonArray = mContentListServiceHandler.getmViewJsonArray();

        for (int i=0;i<viewJsonArray.length();i++){
            try {
                JSONObject jsonObject = viewJsonArray.getJSONObject(i);
                ContentViewsModel contentViewsModel=new ContentViewsModel();
                contentViewsModel.populateDummyData(jsonObject);

                contentViewsModelList.add(contentViewsModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return contentViewsModelList;
    }

    // Method that calls database handlers method to populate json data...
    public void populateDatabase(){

        for(int i=0 ;i<mContentInfoModelList.size();i++){
            mLocalDB.insertInfoContentData(mContentInfoModelList.get(i));
        }

        for(int i=0;i<mContentViewsModelList.size();i++){
            mLocalDB.insertViewContentData(mContentViewsModelList.get(i));
        }
    }

    //Method to populate dummy data without rest call....
    public List<ContentViewModelData> populateDummyData(){
        List <ContentViewModelData> contentViewModelDataList = new ArrayList<>();

        for (int i=0;i<5;i++) {
            ContentViewModelData contentViewModelData = new ContentViewModelData();

            contentViewModelData.mPartTitle = "dummy part time";
            contentViewModelData.mTimeTitle = "dummy time";
            contentViewModelData.mMainTitle = "dummy Main title";
            contentViewModelData.mStatusTitle = "dummy status title";
            contentViewModelData.mViewTitle = "dummy View title";

            //Comment to set mMainIcon from url...
//            contentViewModelData.mMainIcon = R.drawable.user;
            contentViewModelData.mShareIcon = R.drawable.share;

            contentViewModelDataList.add(contentViewModelData);
        }
        return contentViewModelDataList;
    }
}
