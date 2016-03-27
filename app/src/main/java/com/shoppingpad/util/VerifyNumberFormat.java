package com.shoppingpad.util;

import android.util.Log;

/**
 * Created by bridgelabz3 on 26/3/16.
 * Purpose:
 *
 * 1. This class return true if mobile number is in valid form
 */
public class VerifyNumberFormat {
    String mNum;
    public boolean verifyNumberFormat(String number){
        boolean flag;
        mNum=number;
        Log.e("ph no: ", number);
        flag = number.matches("^[7-9][0-9]{9}$");
        return flag;
    }
}
