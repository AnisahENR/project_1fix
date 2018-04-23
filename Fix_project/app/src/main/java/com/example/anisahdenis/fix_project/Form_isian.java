package com.example.anisahdenis.fix_project;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Anisah Denis on 4/1/2018.
 */

public class Form_isian extends Fragment {

    int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;

    boolean check = true;
    ImageView imageView;
    public EditText nama, alamat, no_hp, masalah;
    public Spinner kec, kel;
    String nama_1, alamat_1, no_hp_1, kel_1, kec_1, masalah_1, titik_1,titik_2, getLong, getLat;
    public double latitude, longitude;
    public final static int PLACE_PICKER_REQUEST = 1;
    public final static int MY_PERMISSION_FINE_LOCATION = 101;

    ProgressDialog progressDialog ;
    String ImagePath = "image_path" ;
    String ServerUploadPath ="http://sdad.pasuruankota.go.id/v2/uploads/imageupload.php" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.form_isian, container, false);

        requestPermission();
        requestPermission();
        imageView = view.findViewById(R.id.imageView);
        nama =  view.findViewById(R.id.nama);
        alamat = view.findViewById(R.id.alamat);
        no_hp = view.findViewById(R.id.no_hp);
        kel = view.findViewById(R.id.kel);
        kec = view.findViewById(R.id.drop_kec);
        masalah =view.findViewById(R.id.masalah);
        alamat.setOnClickListener(mListener);
        // b_foto =view.findViewById(R.id.foto);
        view.findViewById(R.id.foto).setOnClickListener(mListener);
        view.findViewById(R.id.button2).setOnClickListener(mListener);

     return view;
}
// fungsi ini untuk multiple onclick pada fragment
    private final View.OnClickListener mListener = new View.OnClickListener(){
        public void onClick(View v){

            switch (v.getId()){
                case R.id.foto:
                    showFileChooser();
                    break;
                case R.id.button2:
                    GetData();
                    ImageUploadToServerFunction();
                    break;
                case R.id.alamat:
                   getAlamat();
                    break;

            }

        }
    };


    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    // digunakan untuk mengambil gambar dari gallery
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    // digunakan untuk langsung menuju current lokasi maps atau membuka maps
    //secara otomatis
    private  void getAlamat(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = builder.build(getActivity());
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    // mendefinisikan data untuk dijadikan string
    public  void GetData(){
        nama_1 = nama.getText().toString();
        alamat_1 = alamat.getText().toString();
        no_hp_1 = no_hp.getText().toString();
        kel_1 = kel.getSelectedItem().toString();
        this.kel.getSelectedItem().toString();
        kec_1 = kec.getSelectedItem().toString();
        this.kec.getSelectedItem().toString();
        masalah_1 = masalah.getText().toString();
        titik_1 = getLat;
        titik_2 = getLong;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    getActivity().finish();

                }
                break;
        }
    }

    // untuk mengalihkan ke google maps dan mendapatkan latitude dan longitude dr alamat yang dipilih
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode== RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK && data != null) {
            Place place = PlacePicker.getPlace(getActivity(), data);
            this.latitude = place.getLatLng().latitude;
            this.longitude = place.getLatLng().longitude;
            getLat = Double.toString(latitude);
            getLong = Double.toString(longitude);

            this.alamat.setText("" + place.getAddress());
        }
    }//end activityResult

    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Data sedang di upload","Tunggu sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(getActivity(),string1, Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {


                Form_isian.ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                //HashMapParams.put(ImageName, GetImageNameEditText);
                HashMapParams.put("nama", nama_1);
                HashMapParams.put("alamat", alamat_1);
                HashMapParams.put("no_hp", no_hp_1);
                HashMapParams.put("kelurahan",kel_1);
                HashMapParams.put("kecamatan",kec_1);
                HashMapParams.put("masalah",masalah_1);
                HashMapParams.put("titik",titik_1);
                HashMapParams.put("titik2",titik_2);

                HashMapParams.put(ImagePath, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    } //endimage upload

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }
}

