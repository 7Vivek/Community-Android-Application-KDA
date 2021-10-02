package com.project.kda.activities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.project.kda.R;
import com.project.kda.activities.intro_activities.IntroActivity;
import com.project.kda.activities.intro_activities.MainActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView img_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        img_logo = (ImageView) findViewById(R.id.iv);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        img_logo.startAnimation(myanim);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String email = pref.getString("email", "");
                String pssword = pref.getString("Password", "");
                Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
                if (!isIntroActivityOpnendBefore) {
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (email != null && !email.isEmpty() && pssword != null && !pssword.isEmpty()) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        }, 2000);
    }
}
