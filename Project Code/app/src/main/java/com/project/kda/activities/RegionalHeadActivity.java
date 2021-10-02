package com.project.kda.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.project.kda.R;

public class RegionalHeadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regional_head);

        TextView email=(TextView)findViewById(R.id.adminone);
        email.setText(Html.fromHtml("<a href=\"mailto:limbadsneha777@gmail.com\">limbadsneha777@gmail.com</a>"));
        email.setMovementMethod(LinkMovementMethod.getInstance());

        TextView email1=(TextView)findViewById(R.id.admintwo);
        email1.setText(Html.fromHtml("<a href=\"mailto:viveklimbad1010@gmail.com\">viveklimbad1010@gmail.com</a>"));
        email1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView emailfinal=(TextView)findViewById(R.id.adminkda);
        emailfinal.setText(Html.fromHtml("<a href=\"mailto:kda1959@gmail.com\">kda1959@gmail.com</a>"));
        emailfinal.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
