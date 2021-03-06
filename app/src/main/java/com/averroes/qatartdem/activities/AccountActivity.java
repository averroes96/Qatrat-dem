package com.averroes.qatartdem.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.averroes.qatartdem.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void Save(View view) {
        Toast.makeText(AccountActivity.this, "Information saved!",
                Toast.LENGTH_LONG).show();
    }
    public void MyProfile(View view)
    {
        Intent intent = new Intent(this,AccountActivity.class);
        startActivity(intent);
    }
}