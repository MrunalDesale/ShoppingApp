package com.shoppingpad.servicehandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by bridgelabz3 on 22/3/16.
 * Purpose:
 *
 * 1. This class post the user name and mobile number to server.
 * 2. It also accept response from server after successfully registration...
 */
public class RegistrationServiceHandler {

    URL mUrl;
    String response = "";
    String mPhoneNo,mName;

    public String postData(String phno,String name) throws FileNotFoundException {

        mPhoneNo = "+91-"+phno;
        mName = name;
        String data;
        StringBuilder buffer = new StringBuilder("");
        try {

            //Prepare data passed to server...
            data = URLEncoder.encode("phone_no", "UTF-8") + "=" + URLEncoder
                    .encode(mPhoneNo, "UTF-8");
            data+="&"+URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder
                    .encode(mName, "UTF-8");

            //Url on which data is to be posted...
            mUrl = new URL("http://54.86.64.100:3000/api/v1/user/mobile/"+
                    mPhoneNo+"/"+mName);
            OutputStreamWriter outputStream;

            //Establish connection to url...
            HttpURLConnection urlConnection = (HttpURLConnection) mUrl.openConnection();
            urlConnection.setDoOutput(true);

            //get outputStream to write data to url...
            outputStream = new OutputStreamWriter(urlConnection.getOutputStream());

            //Write data to url...
            outputStream.write(data);
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
            response=buffer.toString();

            //close connection...
            outputStream.close();
            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
