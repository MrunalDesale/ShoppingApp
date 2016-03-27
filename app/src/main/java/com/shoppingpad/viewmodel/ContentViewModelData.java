package com.shoppingpad.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.shoppingpad.BR;

/**
 * Created by bridgelabz3 on 13/3/16.
 * Purpose:
 * 1.This class contains all attributes that are required to set in View and
 * populated in ViewModel.
 * 2.This class is responsible for data binding.
 */
public class ContentViewModelData extends BaseObservable{

    public String mMainIcon;
    public int mShareIcon;

    public String mMainTitle;
    public String mStatusTitle;
    public String mTimeTitle;
    public String mViewTitle;
    public String mPartTitle;

    //Following methods represent the code for binding contents

    @Bindable
    public int getmShareIcon() {
        return mShareIcon;
    }

    public void setmShareIcon(int mShareIcon) {
        this.mShareIcon = mShareIcon;
        notifyPropertyChanged(BR.mShareIcon);
    }

    @Bindable
    public String getmMainTitle() {
        return mMainTitle;
    }

    public void setmMainTitle(String mMainTitle) {
        this.mMainTitle = mMainTitle;
        notifyPropertyChanged(BR.mMainTitle);
    }

    @Bindable
    public String getmTimeTitle() {
        return mTimeTitle;
    }

    public void setmTimeTitle(String mTimeTitle) {
        this.mTimeTitle = mTimeTitle;
        notifyPropertyChanged(BR.mTimeTitle);
    }

    @Bindable
    public String getmStatusTitle() {
        return mStatusTitle;
    }

    public void setmStatusTitle(String mStatusTitle) {
        this.mStatusTitle = mStatusTitle;
        notifyPropertyChanged(BR.mStatusTitle);
    }

    @Bindable
    public String getmViewTitle() {
        return mViewTitle;
    }

    public void setmViewTitle(String mViewTitle) {
        this.mViewTitle = mViewTitle;
        notifyPropertyChanged(BR.mViewTitle);
    }

    @Bindable
    public String getmPartTitle() {
        return mPartTitle;
    }

    public void setmPartTitle(String mPartTitle) {
        this.mPartTitle = mPartTitle;
        notifyPropertyChanged(BR.mPartTitle);
    }

    @Bindable
    public String getmMainIcon() {
        return mMainIcon;
    }

    public void setmMainIcon(String mMainIcon) {
        this.mMainIcon = mMainIcon;
        notifyPropertyChanged(BR.mMainIcon);
    }
}