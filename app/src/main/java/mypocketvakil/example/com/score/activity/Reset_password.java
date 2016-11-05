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

import mypocketvakil.example.com.score.AsyncTask.ResetAsyncTask;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.Utils.AppUtils;

public class Reset_password extends AppCompatActivity {
    String password1, password2;
    String id;
    private TextView tv_reset;
    private EditText et_change_password;
    private EditText et_confirm_password;
    private HashMap postdataparams;
    private RelativeLayout rl_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        tv_reset = (TextView) findViewById(R.id.tv_reset);
        et_change_password = (EditText) findViewById(R.id.et_new_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        rl_reset = (RelativeLayout) findViewById(R.id.rl_reset);
        password1 = et_change_password.getText().toString();
        password2 = et_confirm_password.getText().toString();
        if (password1.equals(password2)) {

            tv_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Reset_password.this);

                    id = String.valueOf(sharedPref.getInt("user_id", -1));
                    postdataparams = new HashMap<String, String>();
                    postdataparams.put("password", et_confirm_password.getText().toString());
                    postdataparams.put("id", id);
                    ResetAsyncTask task = new ResetAsyncTask(Reset_password.this, postdataparams);
                    task.execute();

                }
            });
        }


//        tv_reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent=new Intent(Reset_password.this,Forgot.class);
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
//                    }
//                },100);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Reset_password.this, Forgot.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        }, 50);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public boolean onTouchEvent(MotionEvent event) {
        AppUtils.hideKeyBoard(Reset_password.this);
        return true;
    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack = Snackbar.make(rl_reset, responseString, Snackbar.LENGTH_LONG);
        snack.show();

    }

    public void onSuccessfulSignUp(String responseString) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Reset_password.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            }
        }, 50);
    }
}
