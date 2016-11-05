package mypocketvakil.example.com.score;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import mypocketvakil.example.com.score.activity.Info_wall;
import mypocketvakil.example.com.score.activity.Login;


public class SplashActivity extends AppCompatActivity {
    private String acess_token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                acess_token = sharedPref.getString("access_token", null);
//                acess_token=AppSharedPreference.getString(SplashActivity.this, AppSharedPreference.PREF_KEY.Access_token);


                if (acess_token == null || acess_token == "") {


                    Intent intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, Info_wall.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }


            }


        }, 2000);
    }
}
