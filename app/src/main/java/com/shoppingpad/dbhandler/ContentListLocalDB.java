package com.shoppingpad.dbhandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shoppingpad.model.ContentInfoModel;
import com.shoppingpad.model.ContentViewsModel;

/**
 * Created by bridgelabz3 on 13/3/16.
 * Purpose:
 *
 * 1.This class fetch data from database and also stores data into database.
 * 2.It provides data to controller if internet connection is not established.
 * 3.It contains all tables required for saving the data.
 */
public class ContentListLocalDB extends SQLiteOpenHelper{

    SQLiteDatabase db;
    Context context;

    public static final String DB_NAME="ShoppingAppDatabase";
    public static final int DB_VERSION=2;
    public static final String TABLE_NAME="ShoppingApp";
    public static final String CONTENT_INFO_TABLE="ContentInfo";
    public static final String CONTENT_VIEW_TABLE="ContentView";

    //Table attributes of Content table
    public static final String TITLE="Title";
    public static final String STATUS="Status";
    public static final String VIEWS="Views";
    public static final String PARTICIPANTS="Participants";
    public static final String TIME="Time";

    //Table attributes of ContentInfo table
    public static final String MODIFIED_AT="Modified_at";
    public static final String CREATED_AT="Created_at";
    public static final String SYNC_DATE_TIME="Sync_date_time";
    public static final String DESCRIPTION="Description";
    public static final String CONTENT_LINK="Content_link";
    public static final String IMAGES_LINK="Images_link";
    public static final String DISPLAY_NAME="Display_name";
    public static final String URL="Url";
    public static final String TITLE1="Title";
    public static final String CONTENT_TYPE="Content_type";
    public static final String CONTENT_ID="Content_id";

    //Table attributes of ContentView table
    public static final String NO_OF_VIEWS="No_of_views";
    public static final String LAST_VIEWED_DATE_TIME="Last_viewed_date_time";
    public static final String DISPLAY_PROFILE="Display_profile";
    public static final String EMAIL="Email";
    public static final String ACTION="Action";
    public static final String NO_OF_PARTICIPANTS="No_of_participants";
    public static final String LAST_NAME="Last_name";
    public static final String FIRST_NAME="First_name";
    public static final String USER_ID="User_id";
    public static final String CONTENT_ID1="Content_id";
    public static final String USER_ADMIN_ID="User_admin_id";
    public static final String USER_CONTENT_ID="User_content_id";

    //Query to create Dummy table...
    public static final String CREATE_QUERRY="CREATE TABLE "+TABLE_NAME+"("+
            TITLE+" TEXT,"+STATUS+" TEXT,"+VIEWS+" TEXT,"+PARTICIPANTS+" TEXT,"+
            TIME+" TEXT);";

    //Query to create ContentInfo table...
    public static final String CONTENT_INFO_QUERRY="CREATE TABLE "+CONTENT_INFO_TABLE+"("+
            MODIFIED_AT+" TEXT,"+CREATED_AT+" TEXT,"+SYNC_DATE_TIME+" TEXT,"+
            DESCRIPTION+" TEXT,"+CONTENT_LINK+" TEXT,"+IMAGES_LINK+" TEXT,"+
            DISPLAY_NAME+" TEXT,"+URL+" TEXT,"+TITLE1+" TEXT,"+CONTENT_TYPE+" TEXT,"
            +CONTENT_ID+" TEXT"+");";
    //Query to create ContentView table...
    public static final String CONTENT_VIEW_QUERRY="CREATE TABLE "+CONTENT_VIEW_TABLE+"("+
            NO_OF_VIEWS+" TEXT,"+NO_OF_PARTICIPANTS+" TEXT,"+LAST_VIEWED_DATE_TIME+" TEXT,"+DISPLAY_PROFILE+" TEXT,"+
            EMAIL+" TEXT,"+ACTION+" TEXT,"+LAST_NAME+" TEXT,"+
            FIRST_NAME+" TEXT,"+USER_ID+" TEXT,"+CONTENT_ID1+" TEXT,"+USER_ADMIN_ID+" TEXT,"
            +USER_CONTENT_ID+" TEXT"+");";

    ////Query to retrieve data from table...
    public static final String RETRIEVE_QUERRY="SELECT * FROM "+TABLE_NAME;
    public static final String RETRIEVE_VIEW_CONTENT="SELECT * FROM "+CONTENT_VIEW_TABLE;
    public static final String RETRIEVE_INFO_CONTENT="SELECT * FROM "+CONTENT_INFO_TABLE;

    public ContentListLocalDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=getWritableDatabase();
        Log.e("db constructor","db constructor");
        this.context=context;
    }

    //Code to create all table query...
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERRY);
        db.execSQL(CONTENT_INFO_QUERRY);
        db.execSQL(CONTENT_VIEW_QUERRY);
    }

    //Code to update all table query if table is updated...
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CONTENT_VIEW_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONTENT_INFO_TABLE);
        onCreate(db);
    }

    //Method to insert content in ViewContent table...
    public void insertViewContentData(ContentViewsModel contentViewsModel){
        //Call to method that insert value in view table
        insertViewData(contentViewsModel.mUserContentId, contentViewsModel.
                        mUserAdminId, contentViewsModel.mContentId, contentViewsModel.mUserId,
                contentViewsModel.mFirstName, contentViewsModel.mLastName,
                contentViewsModel.mEmail, contentViewsModel.mDisplayProfile,
                contentViewsModel.mLastViewedDateTime, contentViewsModel.mNoOfViews,
                contentViewsModel.mNumberOfParticipant, contentViewsModel.mAction);

    }
    //Method to insert content in InfoContent table...
    public void insertInfoContentData(ContentInfoModel contentInfoModel){
        //Call to method that insert value in info table
        insertInfoData(contentInfoModel.mContentId, contentInfoModel.mContentType,
                contentInfoModel.mTitle, contentInfoModel.mUrl, contentInfoModel.
                        mDisplayName, contentInfoModel.mImagesLink, contentInfoModel.
                        mContentLink, contentInfoModel.mDescription, contentInfoModel.
                        mSynchDateTime, contentInfoModel.mCreatedAt, contentInfoModel.
                        mModifiedAt);
    }

    //Method that insert data fetched from json to table
    public void insertInfoData(String contentId, String contentType,String title,
                               String url,String displayName,String imagesLink,
                               String contentLink,String description,String syncDateTime,
                               String createdAt,String modifiedAt){
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
            contentValues.put(MODIFIED_AT,modifiedAt);
            contentValues.put(CREATED_AT,createdAt);
            contentValues.put(SYNC_DATE_TIME,syncDateTime);
            contentValues.put(DESCRIPTION,description);
            contentValues.put(CONTENT_LINK,contentLink);
            contentValues.put(IMAGES_LINK,imagesLink);
            contentValues.put(DISPLAY_NAME,displayName);
            contentValues.put(URL,url);
            contentValues.put(TITLE,title);
            contentValues.put(CONTENT_TYPE,contentType);
            contentValues.put(CONTENT_ID, contentId);

        db.insert(CONTENT_INFO_TABLE, null, contentValues);
    }

    //Method that insert data fetched from json to table
    public void insertViewData(String userContentId,String userAdminId,String contentId,
                               String userId,String firstName,String lastName,
                               String email,String displayProfile,String
                               lastViewedDateTime,String numberOfViews,String
                               numberofparticipant,String action){

        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(NO_OF_VIEWS,numberOfViews);
        contentValues.put(LAST_VIEWED_DATE_TIME,lastViewedDateTime);
        contentValues.put(DISPLAY_PROFILE,displayProfile);
        contentValues.put(EMAIL,email);
        contentValues.put(ACTION,action);
        contentValues.put(LAST_NAME,lastName);
        contentValues.put(FIRST_NAME,firstName);
        contentValues.put(USER_ID,userId);
        contentValues.put(NO_OF_PARTICIPANTS,numberofparticipant);
        contentValues.put(CONTENT_ID,contentId);
        contentValues.put(USER_ADMIN_ID,userAdminId);
        contentValues.put(USER_CONTENT_ID,userContentId);

        db.insert(CONTENT_VIEW_TABLE, null, contentValues);
        Log.e("view inserted", "view inserted");
    }

    //Methods to retrieve data from database but not in used right now...
    public Cursor retrieveRecord(){
        db=getWritableDatabase();
        return db.rawQuery(RETRIEVE_QUERRY,null);
    }

    public Cursor getContentInfoData(){
        db=getWritableDatabase();
        return db.rawQuery(RETRIEVE_INFO_CONTENT,null);
    }

    public Cursor getContentViewData(){
        db=getWritableDatabase();
        return db.rawQuery(RETRIEVE_VIEW_CONTENT,null);
    }
}