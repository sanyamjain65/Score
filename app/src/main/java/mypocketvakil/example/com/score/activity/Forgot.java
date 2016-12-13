package mypocketvakil.example.com.score.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import mypocketvakil.example.com.score.AsyncTask.ForgotAsyncTask;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.Utils.AppUtils;

public class Forgot extends AppCompatActivity {
    String id;
    private TextView tv_forgot_send;
    private EditText et_forgot_email;
    private HashMap postdataparams;
    private RelativeLayout rl_forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        tv_forgot_send = (TextView) findViewById(R.id.tv_forgot_send);
        et_forgot_email = (EditText) findViewById(R.id.et_forgot_email);
        rl_forgot = (RelativeLayout) findViewById(R.id.rl_forgot);
        tv_forgot_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Forgot.this);

                id = String.valueOf(sharedPref.getInt("id", -1));
                postdataparams = new HashMap<String, String>();
                postdataparams.put("email", et_forgot_email.getText().toString());
//                postdataparams.put("user_id", id);
                ForgotAsyncTask task = new ForgotAsyncTask(Forgot.this, postdataparams);
                task.execute();
            }
        });


//        tv_forgot_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent=new Intent(Forgot.this,Reset_password.class);
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
//                    }
//                },100);
//
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Forgot.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 50);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public boolean onTouchEvent(MotionEvent event) {
        AppUtils.hideKeyBoard(Forgot.this);
        return true;
    }

    public void onSuccessfulSignUp(String responseString) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Forgot.this, Reset_password.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        }, 50);


    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack = Snackbar.make(rl_forgot, responseString, Snackbar.LENGTH_LONG);
        snack.show();

    }
}
