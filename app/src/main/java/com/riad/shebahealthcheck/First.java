package com.riad.shebahealthcheck;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

public class First extends AppCompatActivity {

    public ImageButton Meas;
    public Button acc;
    public EditText ed1,ed2;
    private Toast mainToast;
    public static String passStr,usrStr,checkpassStr,usrStrlow;
    UserDB check = new UserDB(this);
    CheckBox chkRememberMe;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 555;
    private static final int MY_PERMISSIONS_REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Meas = (ImageButton) findViewById(R.id.prime);
        acc = (Button) findViewById(R.id.newacc);
        ed1 = (EditText) findViewById(R.id.edtu1);
        ed2 = (EditText) findViewById(R.id.edtp1);
        chkRememberMe = (CheckBox) findViewById(R.id.checkBoxRemember);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        showAlert();
        
        
        if (saveLogin == true) {
            ed1.setText(loginPreferences.getString("username", ""));
            ed2.setText(loginPreferences.getString("password", ""));
            chkRememberMe.setChecked(true);
        }

        Meas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(First.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(First.this, new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }


                else {
                    login();
                }
            }
        });


        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(First.this,Login.class);
                startActivity(i);
            }
        });

//        acc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(First.this)
//                        .setTitle("DISCLAIMER")
//                        .setMessage(HtmlCompat.fromHtml("<font color='#900000'>" +
//                                "This app collects location data for providing specific fitness service.This location data will be use in the walking counter fitness service for counting steps,in coordination with GPS."+
//                                "<br><br>Do you allow this app to access your location?" +
//                                "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY))
//
////                        .setNegativeButton(android.R.string.no, null)
//                        .setNegativeButton("DENY", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                First.super.onBackPressed();
//                                finish();
//                                System.exit(0);
//                                dialog.cancel();
//                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        })
//
//                        .setPositiveButton("ALLOW", (arg0, arg1) -> {
//                            Intent i = new Intent(v.getContext(), Login.class);
//                            startActivity(i);
//
////                            First.super.onBackPressed();
////                            finish();
////                            System.exit(0);
//                        }).create().show();
//
//
//
//            }
//        });

    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DISCLAIMER");
        builder.setMessage(HtmlCompat.fromHtml("<font color='#900000'>" +
                "This App is not intended  for medical use, it is only designed for General Fitness & Wellness purposes."+
                "This App collects location data for provids closed or not in use. The location data will be used in the walking counter fitness service for counting steps in coordination with GPS."+

                "<br><br>Do you allow this app to access your location?" +
                "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        builder.setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when OK is clicked
            }
        });
        builder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Finish the app when Deny is clicked
                finishAffinity();
                System.exit(0);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void login(){
        usrStrlow = ed1.getText().toString();
        passStr = ed2.getText().toString();
        usrStr=usrStrlow.toLowerCase();
        if (usrStr.equals("user") && passStr.equals("abcdefgh")) {
            // If the default credentials are entered, proceed with a predefined action
            Intent i = new Intent(First.this, Primary.class); // Replace SomeActivity with the activity you want to navigate to
            startActivity(i);
            finish();
            return;
        }

        if (usrStr.length() < 3 || usrStr.length() > 20) {
            mainToast = Toast.makeText(getApplicationContext(), "You have deleted your account already,you no longer have any account,Please register for new account", Toast.LENGTH_SHORT);
            mainToast.show();
        }

        if (passStr.length() < 3 || passStr.length() > 20) {
            mainToast = Toast.makeText(getApplicationContext(), "Password length must be between 3-20 characters", Toast.LENGTH_SHORT);
            mainToast.show();
        }

        else if ( passStr.isEmpty() || usrStr.isEmpty()) {

            mainToast = Toast.makeText(getApplicationContext(), "You have deleted your account already,you no longer have any account,Please register for new account", Toast.LENGTH_LONG);
            mainToast.show();


        }

        else{

            checkpassStr = check.checkPass(usrStr);

            if(passStr.equals(checkpassStr))
            {

                if (chkRememberMe.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", usrStr);
                    loginPrefsEditor.putString("password", passStr);
                    loginPrefsEditor.apply();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                Intent i = new Intent(First.this, Primary.class);
                i.putExtra("Usr",usrStr);
                startActivity(i);
                finish();

            }

            else {
                //Toast something
                mainToast = Toast.makeText(getApplicationContext(), "You have deleted your account already,you no longer have any account,Please register for new account", Toast.LENGTH_LONG);
                mainToast.show();
            }
        }
    }

    private void  requestPermissionCamera(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }
    private void  requestPermissionCall(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_PHONE_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    login();
                } else {
                    requestPermissionCamera(this);
                }
            }
            case MY_PERMISSIONS_REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    login();
                } else {
                    requestPermissionCall(this);
                }
            }
        }
    }
}
