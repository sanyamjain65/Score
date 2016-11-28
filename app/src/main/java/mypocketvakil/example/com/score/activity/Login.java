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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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




        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login_forget = (TextView) findViewById(R.id.tv_login_forgot);
        tv_login_signup = (TextView) findViewById(R.id.tv_login_signup);
        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);

        tv_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Login.this, Sign_up.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//
//
//
                    }
                }, 50);
            }
            });

        tv_login_forget.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Login.this, Forgot.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                }, 50);
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdataparams = new HashMap<String, String>();
                postdataparams.put("email", et_login_email.getText().toString());
                postdataparams.put("password", et_login_password.getText().toString());
                LoginAsyncTask task = new LoginAsyncTask(Login.this, postdataparams);
                task.execute();
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email1 = et_login_email.getText().toString();
                if (!isValidEmail(email1)) {
                    et_login_email.setError("Invalid Email");
                }

                final String pass = et_login_password.getText().toString();
                if (!isValidPassword(pass)) {
                    et_login_password.setError("less than 6 char");
                }

            }
        });
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
                Intent intent = new Intent(Login.this, Info_wall.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        }, 50);
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(Login.this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("access_token", "kjdskjcnsdk");
        editor.commit();
        editor.apply();
    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack = Snackbar.make(rl_login, responseString, Snackbar.LENGTH_LONG);
        snack.show();

    }
}
