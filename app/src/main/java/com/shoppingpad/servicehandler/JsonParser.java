package com.shoppingpad.servicehandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.NetworkOnMainThreadException;
/**
 * Created by bridgelabz3 on 16/3/16.
 * Purpose:
 *
 * 1. This class contains method which establish connection to the specified
 * url and fetches Json data from same url.
 */
public class JsonParser {

    InputStream mInputStream;
    StringBuffer mBuffer;
    JSONArray mJArray;
    public JsonParser(){

    }
    public JSONArray getJSONFromUrl(String infoUrl)
    {
        mBuffer=new StringBuffer();
        try {
            URL url = new URL(infoUrl);
            //Establish connection and open url connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            mInputStream = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader
                    (mInputStream));
            String read = null;
            //Read json data
            while ((read = in.readLine())!= null)
            {
                mBuffer.append(read);
            }
            //Add json data to json array
            mJArray=new JSONArray(mBuffer.toString());
        } catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return mJArray;
    }
}