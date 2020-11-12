package com.averroes.qatartdem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.averroes.qatartdem.R;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    }

    public void GoToDonnors(View view) {
        Intent intent = new Intent(this,DonnerActivity.class);
        startActivity(intent);
    }

    public void SocialMedia(View view) {
        Intent textShareIntent = new Intent(Intent.ACTION_SEND);
        textShareIntent.putExtra(Intent.EXTRA_TEXT, "My name is Amine Smahi and i need AB+ blood in urgency!");
        textShareIntent.setType("text/plain");
        startActivity(textShareIntent);
    }
    public void MyProfile(View view)
    {
        Intent intent = new Intent(this,AccountActivity.class);
        startActivity(intent);
    }
}