package com.shoppingpad.servicehandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bridgelabz3 on 22/3/16.
 * Purpose:
 *
 * 1. This class post the user name and mobile number to server.
 * 2. It also accept response from server after successfully registration...
 */
public class RegistrationServiceHandler {

    URL mUrl;
    String mResponse = "";
    String mPhoneNo,mName;

    public String postData(String phno,String name,String encodedImage) throws FileNotFoundException {

        mPhoneNo = "+91-"+phno;
        mName = name;
        StringBuilder buffer = new StringBuilder("");
        try {

            //Url on which data is to be posted...
            mUrl = new URL("http://54.86.64.100:3000/api/v1/test/postdata/"+mPhoneNo+"/"+mName);

            OutputStreamWriter outputStream;

            //Establish connection to url...
            HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Cache-Control", "no-cache");
            urlConnection.setRequestProperty("User-agent", "mozilla");
            urlConnection.setRequestProperty("Content-Type", "image/jpeg");

            //get outputStream to write data to url...
            outputStream = new OutputStreamWriter(urlConnection.getOutputStream());

            //Write data to url...
            outputStream.write(encodedImage);
            outputStream.flush();

            //Code to fetch response from url...
            //Get inputStream to read data from url...
            InputStream inputStream = urlConnection.getInputStream();

            //Read data...
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = rd.readLine()) != null) {

                //Write data to buffer...
                buffer.append(line);
            }

            //Get response message fetched from server...
            mResponse=buffer.toString();

            //close connection...
            outputStream.close();
            urlConnection.disconnect();

        } catch (IOException e){
            e.printStackTrace();
        }
        return mResponse;
    }
}