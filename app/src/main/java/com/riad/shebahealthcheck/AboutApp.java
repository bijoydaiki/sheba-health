package com.riad.shebahealthcheck;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AboutApp extends AppCompatActivity {

    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);



//        mail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto","shebaone2021@gmail.com", null));
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Complain/Query");
//                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
//            }
//        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getString("Usr");
        }
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(AboutApp.this, com.riad.shebahealthcheck.Primary.class);
        i.putExtra("Usr", user);
        startActivity(i);
        finish();
        super.onBackPressed();

    }
}
