package com.averroes.qatartdem.includes;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Commons {

    public static void loadImage(String url, int placeHolderID, ImageView imageView){

        try{
            Picasso.get().load(url).placeholder(placeHolderID).into(imageView);
        }catch(Exception e){
            imageView.setImageResource(placeHolderID);
        }

    }
}
