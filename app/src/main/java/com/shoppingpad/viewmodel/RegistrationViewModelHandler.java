package com.shoppingpad.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.shoppingpad.controller.ContentListController;
/**
 * Created by bridgelabz3 on 21/3/16.
 * Purpose:
 *
 * 1.This class accepts all requests from View and response data to view.
 * 2.Its pass only required data to view instead of giving all the data.
 */
public class RegistrationViewModelHandler {

    private static final boolean IS_UNIT_TEST=true;

    boolean result;
    ContentListController mContentListController;
    String mFileName;
    Bitmap mBitmap;

    public String setUserData(String phno, String name,String fileName,String encodedImage){
        mContentListController=new ContentListController();
        String response;
        response=mContentListController.setUserInfo(phno,name,fileName,encodedImage);
        return response;
    }

    //Return result to view received from controller...
    public boolean returnResult(){
        return result;
    }

    public int getOtp(){
        //Get 6 digits OTP
        mContentListController=new ContentListController();
       return mContentListController.getOtp();
    }

    public void setOtp(EditText textV1,String phoneNo,Context context){

        //Set alignment of text in EditText...
        textV1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        //Get OTP
        int otp=getOtp();

        //Send SMS to that mobile number...
//        sendSms(phoneNo, otp, context);

        //Set OTP to EditText...
        textV1.setText("" + otp);
    }

    //Sending SMS code...
    public void sendSms(String phno,int otp,Context context) {
        try {

            //SMS to be send...
            //Message after confirmation of OTP and mobile number...
            String message="Your number is verified. You have successfully registered" +
                    " shopping pad app. Your one time number is "+otp;

            //Get default instance of SmsManager
            SmsManager smsManager = SmsManager.getDefault();

            //Send sms to phno number...
            smsManager.sendTextMessage(phno, null, message, null, null);

            //Call intent that extends BroadcastReceiver...
            Intent intent=new Intent("android.provider.Telephony.SMS_RECEIVED");
            context.sendBroadcast(intent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
