package com.example.anisahdenis.fix_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Anisah Denis on 4/5/2018.
 */

public class Splashscreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalshscreen);

        //Animation anim = new Animation();
        //anim.setAnimationListener();

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(Splashscreen.this, Halaman_awal.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
