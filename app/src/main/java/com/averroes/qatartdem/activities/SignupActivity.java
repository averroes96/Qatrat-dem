package com.averroes.qatartdem.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.averroes.qatartdem.R;
import com.averroes.qatartdem.includes.CameraPermissions;
import com.averroes.qatartdem.includes.LocationPermissions;
import com.averroes.qatartdem.includes.StoragePermissions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity implements CameraPermissions, StoragePermissions, LocationPermissions, LocationListener {

    private ImageView pictureIV;
    private EditText fullnameET,phoneET,wilayaET,dayraET,weightET,heightET,diseasesET,donationsET,emailET,passwordET,confirmPasswordET;
    private Button signupBtn;
    private TextView genderET,birthDateET,typeET,bloodET;

    private String fullName,phone,wilaya,dayra,gender,birthDate,type,blood,weight,height,disease,donations,email,password,confirmPassword;

    private Uri imageUri;

    private String[] locationPerm;
    private String[] cameraPerm;
    private String[] storagePerm;
    private double latitude=0.0,longitude=0.0;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        pictureIV = findViewById(R.id.pictureIV);
        fullnameET = findViewById(R.id.fullnameET);
        phoneET = findViewById(R.id.phoneET);
        wilayaET = findViewById(R.id.wilayaET);
        dayraET = findViewById(R.id.dayraET);
        genderET = findViewById(R.id.genderET);
        birthDateET = findViewById(R.id.birthDateET);
        typeET = findViewById(R.id.typeET);
        bloodET = findViewById(R.id.bloodET);
        weightET = findViewById(R.id.weightET);
        heightET = findViewById(R.id.heightET);
        diseasesET = findViewById(R.id.diseasesET);
        donationsET = findViewById(R.id.donationsET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);
        signupBtn = findViewById(R.id.signupBtn);

        locationPerm = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        cameraPerm = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        storagePerm = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.wait);
        progressDialog.setCanceledOnTouchOutside(false);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInputs();
            }
        });

        wilayaET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLocationPermission()){
                    detectLocation();
                }
                else{
                    requestLocationPermission();
                }
            }
        });

        dayraET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLocationPermission()){
                    detectLocation();
                }
                else{
                    requestLocationPermission();
                }
            }
        });

        pictureIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });
        
        genderET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceDialog(genderET, R.string.your_gender, R.array.genders);
            }
        });
        genderET.setText(getResources().getStringArray(R.array.genders)[0]);

        typeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceDialog(typeET, R.string.your_type, R.array.user_type);
            }
        });
        typeET.setText(getResources().getStringArray(R.array.user_type)[0]);

        bloodET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceDialog(bloodET, R.string.your_blood, R.array.blood_type);
            }
        });
        bloodET.setText(getResources().getStringArray(R.array.blood_type)[0]);

        birthDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


    }

    private void showDateDialog() {

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                birthDateET.setText(i2 + "/" + (i1+1) + "/" + i );
            }
        },year, month, day);
        datePickerDialog.show();
    }

    private void choiceDialog(final TextView et, int titleID, final int resID){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(titleID))
                .setItems(resID, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] array = getResources().getStringArray(resID);
                        et.setText(array[i]);
                    }
                })
                .create().show();

    }

    private void checkInputs() {

        fullName = fullnameET.getText().toString().trim();
        phone = phoneET.getText().toString().trim();
        wilaya = wilayaET.getText().toString().trim();
        dayra = dayraET.getText().toString().trim();
        gender = genderET.getText().toString().trim();
        birthDate = birthDateET.getText().toString().trim();
        type = typeET.getText().toString().trim();
        blood = bloodET.getText().toString().trim();
        weight = weightET.getText().toString().trim();
        height = heightET.getText().toString().trim();
        disease = diseasesET.getText().toString().trim();
        donations = donationsET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        confirmPassword = confirmPasswordET.getText().toString().trim();

        if(TextUtils.isEmpty(fullName)){
            Toast.makeText(this, getString(R.string.enter_valid_name), Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, getString(R.string.enter_valid_phone), Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(wilaya) || TextUtils.isEmpty(dayra)){
            Toast.makeText(this, getString(R.string.enter_location), Toast.LENGTH_LONG).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, getString(R.string.enter_valid_email), Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(this, getString(R.string.short_password), Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(confirmPassword)){
            Toast.makeText(this, getString(R.string.unmatched_passwords), Toast.LENGTH_LONG).show();
            return;
        }
        if(!TextUtils.isDigitsOnly(height) || !TextUtils.isDigitsOnly(height)){
            Toast.makeText(this, getString(R.string.enter_valid_ibm), Toast.LENGTH_LONG).show();
            return;
        }

        createAccount();
    }

    private void createAccount() {
        
        progressDialog.setMessage(getString(R.string.creating_account));
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        saveFireBaseData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveFireBaseData() {

        progressDialog.setMessage(getString(R.string.saving_info));

        String timestamp = "" + System.currentTimeMillis();
        final HashMap<String, Object> data = new HashMap<>();

        data.put("uid", firebaseAuth.getUid());
        data.put("fullname", fullName);
        data.put("phone", phone);
        data.put("email", email);
        data.put("dayra", dayra);
        data.put("wilaya", wilaya);
        data.put("password", password);
        data.put("type", type.equals(getResources().getStringArray(R.array.user_type)[0])? "donner" : "patient");
        data.put("birth_date", birthDate);
        data.put("timestamp", timestamp);
        data.put("blood", blood);
        data.put("gender", gender);
        data.put("weight", weight);
        data.put("height", height);
        data.put("diseases", disease);
        data.put("donations", donations);
        if(imageUri == null){
            data.put("picture", "");
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            reference.child(firebaseAuth.getUid()).setValue(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            startActivity(new Intent(SignupActivity.this, DonnerActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
        else{
            String filePathAndName = "profile_images/" + firebaseAuth.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());
                                Uri downloadUri = uriTask.getResult();

                            if(uriTask.isSuccessful()){
                                data.put("picture", downloadUri.toString());

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                reference.child(firebaseAuth.getUid()).setValue(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(SignupActivity.this, DonnerActivity.class));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(SignupActivity.this, DonnerActivity.class));
                                                finish();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }

    public void showImagePickDialog() {

        String[] options = { getString(R.string.camera), getString(R.string.gallery)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pick_image)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            if(checkCameraPermission()){
                                pickFromCamera();
                            }else{
                                requestCameraPermission();
                            }
                        }else{
                            if(checkStoragePermission()){
                                pickFromGallery();
                            }
                            else{
                                requestStoragePermission();
                            }
                        }
                    }
                })
                .show();
        
    }

    public void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePerm, STORAGE_REQUEST);
    }

    public void pickFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY);
    }

    public boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPerm, CAMERA_REQUEST);
    }

    public void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "temp_image title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "temp_image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA);
    }

    public boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, locationPerm, LOCATION_REQUEST);
    }

    public void detectLocation() {

        Toast.makeText(this, getString(R.string.wait), Toast.LENGTH_LONG).show();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        catch(SecurityException e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void findAddress() {

        Geocoder geocoder;
        List<Address> addrs;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addrs = geocoder.getFromLocation(latitude, longitude, 1);

            String currentWilaya = addrs.get(0).getLocality();
            String currentDayra = addrs.get(0).getSubLocality();

            wilayaET.setText(currentWilaya);
            dayraET.setText(currentDayra);

        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case LOCATION_REQUEST : {
                if(grantResults.length > 0){
                    boolean locationResult = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(locationResult){
                        detectLocation();
                    }else{
                        Toast.makeText(this, getString(R.string.location_needed), Toast.LENGTH_LONG).show();
                    }
                }
            } break;
            case CAMERA_REQUEST : {
                if(grantResults.length > 0){
                    boolean cameraResult = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageResult = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(cameraResult && storageResult){
                        pickFromCamera();
                    }else{
                        Toast.makeText(this, getString(R.string.camera_required), Toast.LENGTH_LONG).show();
                    }
                }
            } break;
            case STORAGE_REQUEST : {
                if(grantResults.length > 0){
                    boolean locationResult = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(locationResult){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, getString(R.string.storage_required), Toast.LENGTH_LONG).show();
                    }
                }
            } break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        findAddress();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(this, getString(R.string.enable_location), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_CAMERA){
                pictureIV.setImageURI(imageUri);
            }
            else if(requestCode == IMAGE_PICK_GALLERY){
                assert data != null;
                imageUri = data.getData();
                pictureIV.setImageURI(imageUri);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}