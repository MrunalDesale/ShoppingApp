package com.shoppingpad.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;


/**
 * Created by bridgelabz3 on 26/3/16.
 * Purpose:
 *
 * 1.This class sends SMS to particular number.
 */
public class SmsSend extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Check permission...
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                try {
                    Log.e("Incoming SMS", "in try");
                    String msg = intent.getStringExtra("pdus");
                    Log.e("SMS sent","SMS sent");
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}
