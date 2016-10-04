
package mypocketvakil.example.com.score.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mypocketvakil.example.com.score.AsyncTask.SignUpAsyncTask;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.Utils.AppUtils;

public class Sign_up extends AppCompatActivity {
    EditText firstname,lastname,email,password,dob,number;
    RadioButton m,f,tc;
    TextView signup;
    private HashMap postdataparams;
    String gender;
    int phone_number;
    String name;
    RelativeLayout relativeLayout;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstname=(EditText)findViewById(R.id.et_first_name);
        lastname=(EditText)findViewById(R.id.et_last_name);
        email=(EditText)findViewById(R.id.et_email);
        password=(EditText)findViewById(R.id.et_password);
        dob=(EditText)findViewById(R.id.et_dob);
        number=(EditText)findViewById(R.id.et_phone);
        m=(RadioButton)findViewById(R.id.rb_m);
        f=(RadioButton)findViewById(R.id.rb_f);
        signup=(TextView)findViewById(R.id.tv_signup);
        relativeLayout=(RelativeLayout)findViewById(R.id.rl_signup);

//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String email1 = email.getText().toString();
//                if (!isValidEmail(email1)) {
//                    email.setError("Invalid Email");
//                }
//
//                final String pass = password.getText().toString();
//                if (!isValidPassword(pass)) {
//                    password.setError("less than 6 char");
//                }
//
//            }
//            });








        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="male";

            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender="female";
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname=firstname.getText().toString();
                String lname=lastname.getText().toString();
                name= fname+lname;
                postdataparams=new HashMap<String,String>();
                postdataparams.put("name",name);
                postdataparams.put("password",password.getText().toString());
                postdataparams.put("mobile_no",number.getText().toString());
                postdataparams.put("email",email.getText().toString());
                postdataparams.put("gender",gender);
                postdataparams.put("dob",dob.getText().toString());
                SignUpAsyncTask task=new SignUpAsyncTask(Sign_up.this,postdataparams);
                task.execute();
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
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Sign_up.this,Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
                finish();
            }
        },50);

    }

    public boolean onTouchEvent(MotionEvent event) {
        AppUtils.hideKeyBoard(Sign_up.this);
        return true;
    }
    public void onSuccessfulSignUp(String responseString) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Sign_up.this,Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left,R.anim.slide_right);
                finish();
            }
        },50);

    }
//        Toast.makeText(SignUpActivity.this,responseString,Toast.LENGTH_SHORT).show();

    public void onSignUpFailed(String responseString) {
        Snackbar snack=Snackbar.make(relativeLayout,responseString,Snackbar.LENGTH_LONG);
        snack.show();






//        Snackbar snack = Snackbar.make(relative_signUp_root, responseString, Snackbar.LENGTH_LONG);
//        View view = snack.getView();
//        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
//        tv.setTextColor(getResources().getColor(R.color.white));
//        snack.show();
        //Toast.makeText(SignUpActivity.this,responseString,Toast.LENGTH_SHORT).show();

    }

}
