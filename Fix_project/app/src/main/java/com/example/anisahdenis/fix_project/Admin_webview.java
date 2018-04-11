package com.example.anisahdenis.fix_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Anisah Denis on 4/5/2018.
 */

public class Admin_webview extends AppCompatActivity {
    @Nullable

    private WebView webView_1; //membuat variabel view agar bisa akses method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_usulan);

        //digunakan untuk memanggil file web local yang tersimpan di folder asset
        //view.loadUrl(Uri.parse("file:///android_asset/contoh.html").toString());

        webView_1 = (WebView) findViewById(R.id.webview);
        webView_1.getSettings().setLoadsImagesAutomatically(true);
        webView_1.getSettings().setJavaScriptEnabled(true);
        webView_1.getSettings().setDomStorageEnabled(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        webView_1.getSettings().setSupportZoom(true);
        webView_1.getSettings().setBuiltInZoomControls(true);
        webView_1.getSettings().setDisplayZoomControls(false);

        webView_1.getSettings().setJavaScriptEnabled(true);
        webView_1.setWebViewClient(new Admin_webview.MyBrowser());

        webView_1.loadUrl("http://sdad.pasuruankota.go.id/v2/");

    }

    //fungsinya agar halaman web tidak meredirect ke halaman browser
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //ketika disentuh tombol back
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView_1.canGoBack()) {
            webView_1.goBack(); //method goback(),untuk kembali ke halaman sebelumnya
            return true;
        }
        // Jika tidak ada halaman yang pernah dibuka
        // maka akan keluar dari activity (tutup aplikasi)
         return super.onKeyDown(keyCode, event);//jika menggunakan activity
       // return onKeyDown(keyCode, event);
    }
}
