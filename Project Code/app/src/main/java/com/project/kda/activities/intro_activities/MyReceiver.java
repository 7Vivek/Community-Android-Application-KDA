package com.project.kda.activities.intro_activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;

import com.project.kda.R;

public class MyReceiver extends BroadcastReceiver {
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    AlertDialog.Builder builder;
    private AlertDialog alertdialog;

    @Override
    public void onReceive(final Context context, Intent intent) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            networkInfo  =connectivityManager.getActiveNetworkInfo();
            if (networkInfo !=null && networkInfo.isConnected()){

                if (alertdialog!=null) {
                    alertdialog.dismiss();
                }
            }
            else {

                builder =new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.activity_internet,null);
                builder.setView(view);
                builder.setCancelable(false);
                builder.create();
                alertdialog =  builder.show();
                view.findViewById(R.id.cancel_internet)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertdialog.dismiss();                            }
                        });
                view.findViewById(R.id.interner_settings)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                            }
                        });

            }
        }

    }
}

