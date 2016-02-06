package com.example.narcis.sportnews;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Narcis on 2/7/2016.
 */
public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://sportnewsapp.coolpage.biz/";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground() {

    }

    public void fetchUserDataInBackground() {

    }
}
