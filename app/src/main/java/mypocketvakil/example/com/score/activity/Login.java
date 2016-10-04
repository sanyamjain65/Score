package mypocketvakil.example.com.score.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

import mypocketvakil.example.com.score.AsyncTask.LoginAsyncTask;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.Utils.AppUtils;

public class Login extends AppCompatActivity {
    private TextView tv_login;
    private TextView tv_login_forget;
    private RelativeLayout rl_login;
    private HashMap postdataparams;
    private EditText et_login_email;
    private EditText et_login_password;
    private TextView tv_login_signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        tv_login=(TextView)findViewById(R.id.tv_login);
        tv_login_forget=(TextView)findViewById(R.id.tv_login_forgot);
        tv_login_signup=(TextView)findViewById(R.id.tv_login_signup);
        et_login_email=(EditText)findViewById(R.id.et_login_email);
        et_login_password=(EditText)findViewById(R.id.et_login_password);
        rl_login=(RelativeLayout)findViewById(R.id.rl_login);

        tv_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(Login.this,Sign_up.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                },50);
            }
        });
        tv_login_forget.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(Login.this,Forgot.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                },50);
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdataparams=new HashMap<String,String>();
                postdataparams.put("email",et_login_email.getText().toString());
                postdataparams.put("password",et_login_password.getText().toString());
                LoginAsyncTask task=new LoginAsyncTask(Login.this,postdataparams);
                task.execute();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public boolean onTouchEvent(MotionEvent event) {
        AppUtils.hideKeyBoard(Login.this);
        return true;
    }

    public void onSuccessfulSignUp(String responseString) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Login.this,Info_wall.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

            }
        },50);
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(Login.this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("access_token", "kjdskjcnsdk");
        editor.commit();
        editor.apply();
    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack=Snackbar.make(rl_login,responseString,Snackbar.LENGTH_LONG);
        snack.show();

    }
}
