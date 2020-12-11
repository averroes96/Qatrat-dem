package com.averroes.qatartdem.includes;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.averroes.qatartdem.R;

public class ProgressButton {

    private CardView progressBtnCV;
    private ConstraintLayout progressBtnCL;
    private ProgressBar progressBar;
    public TextView textView;

    Animation fadeIn;

    public ProgressButton(Context context, View view){

        progressBtnCV = view.findViewById(R.id.progressBtnCV);
        progressBtnCL = view.findViewById(R.id.progressBtnCL);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.textView);

    }

    public void btnActivated(){
        progressBar.setVisibility(View.VISIBLE);
        textView.setText(R.string.wait);
    }

    public void btnFinished(String text){
        progressBar.setVisibility(View.GONE);
        textView.setText(text);
    }
}
