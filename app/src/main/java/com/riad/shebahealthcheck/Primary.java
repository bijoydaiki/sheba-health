package com.riad.shebahealthcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Primary extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String user;
    private int p;
    Button step;
    ImageView imageView_2;
    ImageButton playstore;
    private static final int MY_PERMISSIONS_REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary2);

        Button HeartRate = this.findViewById(R.id.HR);
        ImageButton BloodPressure = this.findViewById(R.id.BP);
        ImageButton Ox2 = this.findViewById(R.id.O2);
        ImageButton RRate = this.findViewById(R.id.RR);
        Button VitalSigns = this.findViewById(R.id.VS);
        ImageButton Abt = this.findViewById(R.id.About);
        Button Instruction = this.findViewById(R.id.id_instruction);
        Button Record = this.findViewById(R.id.id_record);
        step = findViewById(R.id.id_step);

        imageView_2 = findViewById(R.id.imageView2);
        playstore=findViewById(R.id.id_playstore);

        //==========================//

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                String imageURL = "https://raw.githubusercontent.com/riadrayhan/shoe_project/main/step2.jpg";

                try {
                    URL url = new URL(imageURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        Bitmap image = BitmapFactory.decodeStream(inputStream);

                        // Only for making changes in UI
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView_2.setImageBitmap(image);
                            }
                        });
                    } else {

                        finishActivity();
                    }

                    // Disconnect the connection
                    connection.disconnect();
                } catch (Exception e) {

                    finishActivity();
                    e.printStackTrace();
                }
            }
        });



        //====video view start========//

        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
        videoView.start();

        //====video view end========//

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.sheba_appbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id. fab ) ;


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("Usr");
            //The key argument here must match that used in the other activity
        }

        Abt.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), AboutApp.class);
            startActivity(i);
            finish();
        });


        HeartRate.setOnClickListener(v -> {
            p = 1;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();
        });

        BloodPressure.setOnClickListener(v -> {
            p = 2;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();
        });

        RRate.setOnClickListener(v -> {
            p = 3;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();
        });

        Ox2.setOnClickListener(v -> {
            p = 4;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();

        });

        VitalSigns.setOnClickListener(v -> {
            p = 5;
            Intent i = new Intent(v.getContext(), StartVitalSigns.class);
            i.putExtra("Usr", user);
            i.putExtra("Page", p);
            startActivity(i);
            finish();
        });

        Instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Primary.this, Instruction.class);
                startActivity(i);
            }
        });
        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Primary.this, Step_Counter.class);
                startActivity(i);
            }
        });
        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Primary.this, Record_Page.class);
                startActivity(i);
            }
        });

        playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.wiztecbd.shebaone"));
              startActivity(i);
            }
        });


//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setTitle("Really Exit?")
//                .setMessage("Are you sure you want to exit?")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> {
//
//                    Primary.super.onBackPressed();
//                    finish();
//                    System.exit(0);
//                }).create().show();
//    }
//


//        fab.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick (View view) {
//
//                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto","shebaone2021@gmail.com", null));
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Complain/Query");
//                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
//
//                Snackbar. make (view , "Please Wait a Seconds" ,
//                                Snackbar. LENGTH_LONG )
//                        .setAction( "Action" , null ).show() ;
//            }
//        }) ;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setTitle("Really Exit?")
//                .setMessage("Are you sure you want to exit?")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> {
//
//                    Primary.super.onBackPressed();
//                    finish();
//                    System.exit(0);
//                }).create().show();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        getMenuInflater().inflate(R.menu. activity_main_drawer , menu) ;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.id_home) {
            Toast.makeText(getApplicationContext(), "Home Page", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.id_call) {

            Intent i = new Intent(Primary.this, ContactActivity.class);
            startActivity(i);
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:01615573020"));
//                startActivity(intent);
        } else if (id == R.id.About) {
            Intent i = new Intent(Primary.this, AboutApp.class);
            startActivity(i);
        }
        else if (id == R.id.logout) {
            clearApplicationData();
        }
        else if (id == R.id.privacy_policy) {
            Intent i = new Intent(Primary.this, Policy.class);
            startActivity(i);
        }
        else if (id == R.id.logout2) {
            Intent i = new Intent(Primary.this, First.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void clearApplicationData() {
        try {
            // Clearing app data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String packageName = getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear " + packageName);
            }

            // Clearing application cache
            File dir = getCacheDir();
            deleteDir(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    //=======================//
    private void finishActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });

    }
}


//    private void  requestPermissionCall(Activity activity) {
//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_PHONE_CALL);
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//
//            case MY_PERMISSIONS_REQUEST_PHONE_CALL: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    login();
//                } else {
//                    requestPermissionCall(this);
//                }
//            }
//        }
//    }
