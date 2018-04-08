package com.example.anisahdenis.fix_project;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Anisah Denis on 4/1/2018.
 */

public class Form_isian extends Fragment {

    public EditText nama, alamat, no_hp, kel,masalah;
    public Spinner kec;
    String nama_1, alamat_1, no_hp_1, kel_1, kec_1, masalah_1, titik_1,titik_2, getLong, getLat;
    String serverURL ="http://majusdad.pasuruankota.go.id/json/insertData.php";
    public double latitude, longitude;
    public final static int PLACE_PICKER_REQUEST = 1;
    public final static int MY_PERMISSION_FINE_LOCATION = 101;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.form_isian, container, false);

    nama =  view.findViewById(R.id.nama);
    alamat = view.findViewById(R.id.alamat);
    no_hp = view.findViewById(R.id.no_hp);
    kel = view.findViewById(R.id.kel);
    kec = view.findViewById(R.id.drop_kec);
    masalah =view.findViewById(R.id.masalah);
        alamat.setOnClickListener(mListener);
        view.findViewById(R.id.button).setOnClickListener(mListener);

//   foto =  view.findViewById(R.id.foto);
//        button1.setOnClickListener(mListener);
     return view;
}
    private final View.OnClickListener mListener = new View.OnClickListener(){
        public void onClick(View v){

            switch (v.getId()){
                case R.id.alamat:

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    try {
                        Intent intent = builder.build(getActivity());
                        startActivityForResult(intent, PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.button:
                    GetData();
                    InsertData(nama_1, alamat_1, no_hp_1, kel_1,kec_1, masalah_1,titik_1, titik_2);
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
    // mendefinisikan data untuk dijadikan string
    public  void GetData(){
        nama_1 = nama.getText().toString();
        alamat_1 = alamat.getText().toString();
        no_hp_1 = no_hp.getText().toString();
        kel_1 = kel.getText().toString();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK && data != null) {
            Place place = PlacePicker.getPlace(getActivity(), data);
            this.latitude = place.getLatLng().latitude;
            this.longitude = place.getLatLng().longitude;
            getLat = Double.toString(latitude);
            getLong = Double.toString(longitude);

            this.alamat.setText("" + place.getAddress());
        }
    }



    //mendefinisikan method asyncTask untuk insert data ke server
    public void InsertData(final String nama, final String alamat, final String no_hp, final String kel,final String kec,  final String masalah, final String titik, final String titik2){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {


                // String NameHolder = nama ;
//                String EmailHolder = email ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("nama", nama));
                nameValuePairs.add(new BasicNameValuePair("alamat", alamat));
                nameValuePairs.add(new BasicNameValuePair("no_hp", no_hp));
                nameValuePairs.add(new BasicNameValuePair("kelurahan", kel));
                nameValuePairs.add(new BasicNameValuePair("kecamatan", kec));
                nameValuePairs.add(new BasicNameValuePair("masalah", masalah));
                nameValuePairs.add(new BasicNameValuePair("titik",titik ));
                nameValuePairs.add(new BasicNameValuePair("titik2",titik2 ));

//                ContentValues values = new ContentValues();
//                values.put("kecamatan", kec);
                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(serverURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(getActivity(), "Data Submit Successfully", Toast.LENGTH_LONG).show();
                getActivity().finish();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(nama, alamat, no_hp, kel, kec, masalah, titik, titik2);
    }
}

