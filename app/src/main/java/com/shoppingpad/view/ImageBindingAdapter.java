package com.shoppingpad.view;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by bridgelabz3 on 19/3/16.
 */
public class ImageBindingAdapter {

    //Code to bind image...
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url){
        //Binds image to imageView...
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }
}
