package com.project.kda.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.kda.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

       /* More fragment=new More();
        FragmentManager manager=getSupportFragmentManager();

        manager.beginTransaction().add(R.id.aboutlayout,fragment).commit();*/

    }
}
