package com.example.anisahdenis.fix_project;

import android.content.Intent;
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
        button_admin.setOnClickListener(this);
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
                break;
            case  R.id.rating:
                break;
        }
    }
}

