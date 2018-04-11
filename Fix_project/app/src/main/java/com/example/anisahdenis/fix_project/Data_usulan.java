package com.example.anisahdenis.fix_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Anisah Denis on 4/5/2018.
 */

public class Data_usulan extends Fragment {
    @Nullable

    private WebView webView_1; //membuat variabel view agar bisa akses method

    public  Data_usulan(){
    //hanya berfungsi sebagai contructor bisa dibuang juga kalo tidak perlu
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_usulan, container, false);

        webView_1 = (WebView) view.findViewById(R.id.webview);
        webView_1.getSettings().setLoadsImagesAutomatically(true);
        webView_1.getSettings().setJavaScriptEnabled(true);
        webView_1.getSettings().setDomStorageEnabled(true);

        //merupakan fungsi untuk melakukan zoom in / zoom out
        webView_1.getSettings().setSupportZoom(true);
        webView_1.getSettings().setBuiltInZoomControls(true);
        webView_1.getSettings().setDisplayZoomControls(false);

        // digunakan untuk mengaktifkan javascript
        webView_1.getSettings().setJavaScriptEnabled(true);
        // memanggil file web yang ada di server
        webView_1.setWebViewClient(new MyBrowser());
        webView_1.loadUrl("http://sdad.pasuruankota.go.id/v2/view.php"); //link dari server
        return view;
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
        // return super.onKeyDown(keyCode, event);//jika menggunakan activity
        return onKeyDown(keyCode, event);
    }
}

