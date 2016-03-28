package com.shoppingpad.view;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.shoppingpad.R;
import com.squareup.picasso.Picasso;

/**
 * Created by bridgelabz3 on 19/3/16.
 * Purpose:
 *
 * 1.This class is used to Image binding.
 * 2.It is called automatically.
 */
public class ImageBindingAdapter {

    //Code to bind image...
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url){
        //Binds image to imageView...
        Picasso.with(imageView.getContext()).load(url).placeholder(R.drawable.user)
                .into(imageView);
    }
}
