package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class DeliveredActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered);
        scannerView = findViewById(R.id.scanDelivered);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(DeliveredActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(DeliveredActivity.this,"You must accept this permissions",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @Override
    public void handleResult(Result rawResult) {

        Intent myIntent = new Intent(DeliveredActivity.this, SignatureActivity.class);
        myIntent.putExtra("trackingnumber",rawResult.getText());
        DeliveredActivity.this.startActivity(myIntent);
        /*
        String trackingNumber = rawResult.getText();
        HashMap<String,String> parameters = new HashMap<>();
        HashMap<String,String> parameters2 = new HashMap<>();


        //Add the parameters needed for changeStatusToDeliveredByTn
        parameters.put("trackingnumber",trackingNumber);

        //Add the parameters needed for createReport
        parameters2.put("courierid", "1");
        parameters2.put("trackingnumber", trackingNumber);
        parameters2.put("status","Delivered");

        NetworkController.getInstance(getApplicationContext()).createHTTPPostRequest(parameters2,"createReport");
        NetworkController.getInstance(getApplicationContext()).createHTTPPutRequest(parameters,"changeStatusToDeliveredByTn");
        */

    }
}
