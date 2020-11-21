package com.averroes.qatartdem.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.averroes.qatartdem.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton searchBtn, filterBtn;
    private TextView donnersTV,patientsTV;
    private ImageView pictureIV;
    private RecyclerView donnersRV,patientsRV;
    private Button requestBtn;
    String[] maintitle ={
            "Amine Smahi","Oussama Bonnor",
            "Mohammed Benothman","Abed kibbir",
            "Oussama Zauoi",
            "Amine Smahi","Oussama Bonnor",
            "Mohammed Benothman","Abed kibbir",
            "Oussama Zauoi",
            "Amine Smahi","Oussama Bonnor",
            "Mohammed Benothman","Abed kibbir",
            "Oussama Zauoi",
            "Amine Smahi","Oussama Bonnor",
            "Mohammed Benothman","Abed kibbir",
            "Oussama Zauoi",
    };

    String[] subtitle ={
            "2 miles","5 miles",
            "4 miles","7 miles",
            "8 miles",
            "2 miles","5 miles",
            "4 miles","7 miles",
            "8 miles",
            "2 miles","5 miles",
            "4 miles","7 miles",
            "8 miles",
            "2 miles","5 miles",
            "4 miles","7 miles",
            "8 miles",
    };

    Integer[] imgid={
            R.mipmap.avatar1,R.mipmap.avatar2,
            R.mipmap.avatar3,R.mipmap.avatar1,
            R.mipmap.avatar2,
            R.mipmap.avatar1,R.mipmap.avatar2,
            R.mipmap.avatar3,R.mipmap.avatar1,
            R.mipmap.avatar2,
            R.mipmap.avatar1,R.mipmap.avatar2,
            R.mipmap.avatar3,R.mipmap.avatar1,
            R.mipmap.avatar2,
            R.mipmap.avatar1,R.mipmap.avatar2,
            R.mipmap.avatar3,R.mipmap.avatar1,
            R.mipmap.avatar2,
    };

    public void CallHim(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }
    public void ViewProfile(View view)
    {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = findViewById(R.id.searchBtn);
        filterBtn = findViewById(R.id.filterBtn);
        donnersTV = findViewById(R.id.donnersTV);
        patientsTV = findViewById(R.id.patientsTV);
        pictureIV = findViewById(R.id.pictureIV);
        donnersRV = findViewById(R.id.donnersRV);
        patientsRV = findViewById(R.id.patientsRV);
        requestBtn = findViewById(R.id.requestBtn);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        donnersTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        patientsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void GoSearch(View view) {
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }

    public void Request() {
        if(requestBtn.getText().equals("Make Request")) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked

                            Toast.makeText(MainActivity.this, "You requested for AB+ blood type!",
                                    Toast.LENGTH_LONG).show();
                            requestBtn.setText("Request Status");
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }

            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        else {
            Intent intent = new Intent(this,StatusActivity.class);
            startActivity(intent);
        }
    }
    public void MyProfile(View view)
    {
        Intent intent = new Intent(this,AccountActivity.class);
        startActivity(intent);
    }
}
