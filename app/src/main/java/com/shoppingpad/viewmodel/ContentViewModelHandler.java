package com.shoppingpad.viewmodel;

import android.content.Context;
import android.util.Log;

import com.shoppingpad.R;
import com.shoppingpad.controller.ContentListController;
import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ContentViewsModel;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgelabz3 on 13/3/16.
 * Purpose:
 *
 * 1.This class is responsible for accepting all data from controller and pass
 * only required data to View
 * 2.This class filters data and pass that data to view
 */

public class ContentViewModelHandler {

    private static final boolean IS_UNIT_TEST=false;

    public List<ContentViewModelData> resultList;

    ContentListController mContentListController;
    public ContentViewModelHandler(Context context)
    {
        if(IS_UNIT_TEST)
            //Calling dummy method if rest call is not present...
            resultList=getDummyData();

        else{
            resultList=new ArrayList<>();
            //Call to controller...
            mContentListController = new ContentListController(context);
        }
    }

    //This method return single object of ViewModel at a time accessed in adapter...
    public ContentViewModelData getSingleData(int position){
        return  resultList.get(position);
    }

    //Return size of resultant list of populated data accessed in adapter...
    public int getListSize(){
        return resultList.size();
    }

    public List<ContentViewModelData> populateData(){

        //Set one by one content using json data...
        for (int i=0;i<mContentListController.getInfoModelList().size();i++) {

            ContentViewModelData contentViewModelData = new ContentViewModelData();

            //Get InfoModel and ViewModel list from controller...
            ContentInfoModel contentInfoModel= mContentListController.
                    getInfoModelList().get(i);
            ContentViewsModel contentViewsModel= mContentListController.
                    getContentViewsModelList().get(i);

            //Binds all elements using Observable ViewModelData...

            contentViewModelData.setmMainTitle(contentInfoModel.mContentType);
            contentViewModelData.setmStatusTitle(contentInfoModel.mDisplayName);
            contentViewModelData.setmPartTitle(contentViewsModel.mNumberOfParticipant);
            contentViewModelData.setmViewTitle(contentViewsModel.mNoOfViews);
            contentViewModelData.setmPartTitle(contentViewsModel.mFirstName);
            contentViewModelData.setmTimeTitle(contentViewsModel.mLastViewedDateTime);

            String profilePicUrl=contentViewsModel.mDisplayProfile;
            contentViewModelData.setmMainIcon(profilePicUrl);

            //Code to test contents are setting without using set method...
//            contentViewModelData.mMainTitle = contentInfoModel.mContentType;
//            contentViewModelData.mStatusTitle = contentInfoModel.mDisplayName;
//            contentViewModelData.mViewTitle = contentViewsModel.mNoOfViews;
//            contentViewModelData.mTimeTitle = contentViewsModel.mLastViewedDateTime;
//            contentViewModelData.mPartTitle = contentViewsModel.mFirstName;

            //Set an image fetched from given url...
//            String url="http://www.hdpicswale.in/assets/upload/bollywood-wallpapers/" +
//                    "farhan-akhtar-307/farhan-akhtar-latest-stills-7566.jpeg";
//            contentViewModelData.setmMainIcon(url);
            contentViewModelData.mShareIcon = R.drawable.share;

            //Add ViewModel object to list that is passed to adapter...
            resultList.add(contentViewModelData);
        }
        return resultList;
    }

    //Code for connection between ViewModel and View...
    public List<ContentViewModelData> getDummyData(){

        List<ContentViewModelData> reqData=new ArrayList<>();

        //Initialise dummy data to variables
        int shareIcon=R.drawable.share;

        String mainTitle= "Title1";
        String statusTitle="clicked";
        String timeTitle= "8.30pm";
        String viewTitle= "220Views";
        String partTitle= "101participants";

        //Set data to ContentViewModelHandler object to add in list...
        for (int i=0;i<5;i++){
            ContentViewModelData current=new ContentViewModelData();
            //Comment for binding main image...
//            current.mMainIcon=mainIcon;
            current.mMainTitle=mainTitle;
            current.mPartTitle=partTitle;
            current.mStatusTitle=statusTitle;
            current.mViewTitle=viewTitle;
            current.mShareIcon=shareIcon;
            current.mTimeTitle=timeTitle;

            //Add ContentViewModelHandler object(all data of single view) to list...
            reqData.add(current);
        }
        //return list...
        return reqData;
    }
}
