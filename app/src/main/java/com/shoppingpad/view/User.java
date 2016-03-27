package com.shoppingpad.view;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.Editable;
import android.text.TextWatcher;

import com.shoppingpad.BR;

/**
 * Created by bridgelabz3 on 27/3/16.
 * Purpose:
 *
 * 1.This class contains user mobile number and user name.
 * 2.Data binding is performed in this class to EditText.
 */
public class User extends BaseObservable {
    public String mUserName;
    public String mMobileNo;

    public User(String mUserName,String mMobileNo){
        this.mMobileNo=mMobileNo;
        this.mUserName=mUserName;
    }

    @Bindable
    public String getmUserName(){
        return mUserName;
    }

    @Bindable
    public String getmMobileNo() {
        return mMobileNo;
    }

    public final TextWatcher changed=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!s.toString().equalsIgnoreCase(mUserName))
                setmUserName(s.toString());
            if(!s.toString().equalsIgnoreCase(mMobileNo))
                setmMobileNo(s.toString());
        }
    };
    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
        notifyPropertyChanged(BR.mUserName);
    }
    public void setmMobileNo(String mMobileNo) {
        this.mMobileNo = mMobileNo;
        notifyPropertyChanged(BR.mMobileNo);
    }
}
