package com.example.anisahdenis.fix_project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.R.id.button1;

/**
 * Created by Anisah Denis on 4/1/2018.
 */

public class Halaman_awal extends AppCompatActivity implements View.OnClickListener {

    Button button1,button_admin, button_rating;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_awal);

        button1 = (Button) findViewById(R.id.button1);//button kirim usulan
        button1.setOnClickListener(this);
        button_admin =(Button)findViewById(R.id.admin);
        button_admin.setOnClickListener(this);
        button_rating =(Button) findViewById(R.id.rating);
        button_rating.setOnClickListener(this);
    }
    public static void reviewOnGooglePlay(Activity activity) {
        final String appPackageName = activity.getPackageName();
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
        }
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
    }


    // ini fungsi pada button halaman awal
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case  R.id.button1 :
                Intent intent1;
                intent1 = new Intent(this, TabActivity.class);
                startActivity(intent1);
                break;
            case R.id.admin:
                Intent intent2;
                intent2 = new Intent(this, Admin_webview.class);
                startActivity(intent2);
                break;
            case  R.id.rating:

                Uri uril = Uri.parse("market://details?id="+getPackageName());
                Intent goTomarket = new Intent(Intent.ACTION_VIEW, uril);
                startActivity(goTomarket);
               // reviewOnGooglePlay(this); bisa pakai ini
                break;
        }
    }
}

