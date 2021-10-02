package com.project.kda.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.kda.R;
import com.project.kda.activities.intro_activities.MyReceiver;

public class InternetActivity extends AppCompatActivity {
    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver,intentFilter);
    }
}
