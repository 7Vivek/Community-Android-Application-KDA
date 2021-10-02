package com.project.kda.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.project.kda.R;

public class Admin_Menu extends AppCompatActivity {

    LinearLayout lnr_event,lnr_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__menu);
        lnr_event=(LinearLayout) findViewById(R.id.lnr_event);
        lnr_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_Menu.this, EventListActivity.class)); }
        });

        lnr_img=(LinearLayout) findViewById(R.id.lnr_eventimg);
        lnr_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_Menu.this, ImageListActivity.class)); }
        });
    }
}
