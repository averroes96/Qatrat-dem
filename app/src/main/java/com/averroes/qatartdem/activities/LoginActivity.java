package com.averroes.qatartdem.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.averroes.qatartdem.R;
import com.averroes.qatartdem.includes.ProgressButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button signupBtn;
    private EditText emailTF,passwordTF;
    private CheckBox rememberCB;
    private TextView forgotPasswordTV;
    private View loginBtn;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ProgressButton progressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupBtn = findViewById(R.id.signupBtn);
        emailTF = findViewById(R.id.emailTF);
        passwordTF = findViewById(R.id.passwordTF);
        rememberCB = findViewById(R.id.rememberCB);
        forgotPasswordTV = findViewById(R.id.forgotPasswordTV);
        loginBtn = findViewById(R.id.loginBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.wait);
        progressDialog.setCanceledOnTouchOutside(false);
        progressButton = new ProgressButton(LoginActivity.this, loginBtn);
        progressButton.textView.setText(R.string.log_in);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignup();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressButton.btnActivated();
                GoToDonners();
            }
        });

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void goToSignup() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    public void GoToDonners() {
        String emailText = emailTF.getText().toString().trim();
        String passwordText = passwordTF.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            Toast.makeText(this, getString(R.string.enter_valid_email), Toast.LENGTH_LONG).show();
            progressButton.btnFinished(getString(R.string.log_in));
            return;
        }
        if(TextUtils.isEmpty(passwordText)){
            Toast.makeText(this, getString(R.string.enter_password), Toast.LENGTH_LONG).show();
            progressButton.btnFinished(getString(R.string.log_in));
            return;
        }

        //progressDialog.setMessage(getString(R.string.login_in));
        //progressDialog.show();
        //progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        logIn();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressButton.btnFinished(getString(R.string.log_in));
                        Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void logIn() {

        progressButton.textView.setText(getString(R.string.checking_user));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}