package com.averroes.qatartdem.activities;

import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.averroes.qatartdem.R;
import com.averroes.qatartdem.adapters.UserAdapter;
import com.averroes.qatartdem.modals.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.averroes.qatartdem.includes.Commons.loadImage;

public class MainActivity extends AppCompatActivity {

    private ImageButton searchBtn, filterBtn;
    private TextView donnersTV,patientsTV,fullnameTV;
    private ImageView pictureIV;
    private RecyclerView donnersRV,patientsRV;
    private Button requestBtn;
    private ConstraintLayout donnersCL,patientsCL;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private ArrayList<User> users;
    private UserAdapter userAdapter;
    private String dayra;

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

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.setCanceledOnTouchOutside(false);
        
        checkUser();

        pictureIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });

        donnersTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUsers(dayra, "donner");
                showTab("donners");
            }
        });

        patientsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUsers(dayra, "patient");
                showTab("patients");
            }
        });
    }

    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else{
            loadUserInfo();
        }
    }

    private void loadUserInfo() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                            fullnameTV.setText(dataSnapshot.child("fullname").getValue().toString());
                            dayra = dataSnapshot.child("dayra").getValue().toString();
                            loadImage(dataSnapshot.child("profile_image").getValue().toString(), R.drawable.ic_person_grey, pictureIV);

                            String userType = dataSnapshot.child("type").getValue().toString();
                            if(userType.equals("both") || userType.equals("patient")) {
                                loadUsers(dataSnapshot.child("dayra").getValue().toString(), "donner");
                                showTab("donners");
                            }
                            else {
                                loadUsers(dataSnapshot.child("dayra").getValue().toString(), "patient");
                                showTab("patients");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showTab(String type) {
        if(type.equals("donners")){
            donnersCL.setVisibility(View.VISIBLE);
            patientsCL.setVisibility(View.GONE);

            donnersTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            donnersTV.setBackgroundResource(R.drawable.shape_selected);

            patientsTV.setTextColor(getResources().getColor(R.color.colorWhite));
            patientsTV.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        else{
            donnersCL.setVisibility(View.GONE);
            patientsCL.setVisibility(View.VISIBLE);

            patientsTV.setTextColor(getResources().getColor(R.color.colorPrimary));
            patientsTV.setBackgroundResource(R.drawable.shape_selected);

            donnersTV.setTextColor(getResources().getColor(R.color.colorWhite));
            donnersTV.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }

    private void loadUsers(final String dayra, String type) {

        users = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.orderByChild("type").equalTo(type)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        users.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.child("dayra").getValue().toString().equals(dayra)) {
                                User user = dataSnapshot.getValue(User.class);
                                users.add(user);
                            }
                        }

                        //adapter = new ShopAdapter(MainUserActivity.this, shops);
                        //donnersRV.setAdapter(userAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void GoSearch(View view) {
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }

    public void request() {
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
}
