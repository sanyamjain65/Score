package mypocketvakil.example.com.score.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mypocketvakil.example.com.score.AsyncTask.SignUpAsyncTask;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.Utils.AppUtils;

public class Sign_up extends AppCompatActivity {
    EditText firstname, lastname, email, password, dob, number;
    RadioButton m, f, tc;
    TextView signup;
    String gender;
    int phone_number;
    String name,mail;
    RelativeLayout relativeLayout;
    ProgressDialog progressDialog;
    Calendar myCalendar;
    private HashMap postdataparams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstname = (EditText) findViewById(R.id.et_first_name);

        lastname = (EditText) findViewById(R.id.et_last_name);
        email = (EditText) findViewById(R.id.et_email);

        password = (EditText) findViewById(R.id.et_password);

        dob = (EditText) findViewById(R.id.et_dob);
        myCalendar = Calendar.getInstance();


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                };
                // TODO Auto-generated method stub
                new DatePickerDialog(Sign_up.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        number = (EditText) findViewById(R.id.et_phone);

        m = (RadioButton) findViewById(R.id.rb_m);
        f = (RadioButton) findViewById(R.id.rb_f);
        signup = (TextView) findViewById(R.id.tv_signup);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_signup);


        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f.isChecked()) {
                    f.setChecked(false);


                }


                m.isSelected();

                gender = "male";

            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m.isChecked()) {
                    m.setChecked(false);

                }

                f.isSelected();


                gender = "female";


            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String email1 = email.getText().toString();
                 String pass = password.getText().toString();
                 String pnum = number.getText().toString();
                String dat=dob.getText().toString();
                if (firstname.getText().toString().trim().equals("")) {

                    firstname.setError("First Name is required!");

                }
                else if (email.getText().toString().trim().equals("")) {

                    email.setError("Email is required!");

                }
                else if (!isValidEmail(email1)) {
                    email.setError("Invalid Email");
                }
               else  if (password.getText().toString().trim().equals("")) {

                    password.setError("Password is required!");

                }
                else if (!isValidPassword(pass))
                {
                    password.setError("less than 6 char");
                }
                else if (dat.equals("")) {

                    dob.setError("Date of Birth is required!");

                }
               else if (number.getText().toString().trim().equals("")) {

                    number.setError("Phone number is required!");

                }
                else  if (!isValidPhonenumber(pnum)) {
                    number.setError("Invalid Phone Number");
                }






                else {

//                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//                    String regId = pref.getString("regId", null);
                    String fname = firstname.getText().toString();
                    String lname = lastname.getText().toString();
                    name = fname +" "+ lname;
                    mail=email.getText().toString();
                    postdataparams = new HashMap<String, String>();
                    postdataparams.put("name", name);
                    postdataparams.put("password", password.getText().toString());
                    postdataparams.put("mobile_no", number.getText().toString());
                    postdataparams.put("email", email.getText().toString());
                    postdataparams.put("gender", gender);
                    postdataparams.put("dob", dob.getText().toString());
//                    postdataparams.put("registration_id",regId);
                    SignUpAsyncTask task = new SignUpAsyncTask(Sign_up.this, postdataparams);
                    task.execute();
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

    private boolean isValidPhonenumber(String pnum) {
        if (pnum != null && pnum.length() == 10) {
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

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));
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
                Intent intent = new Intent(Sign_up.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 50);

    }

    public boolean onTouchEvent(MotionEvent event) {
        AppUtils.hideKeyBoard(Sign_up.this);
        return true;
    }

    public void onSuccessfulSignUp(String responseString) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Sign_up.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 50);
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(Sign_up.this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", name);
        editor.putString("email",mail);
        editor.commit();
        editor.apply();


    }
//        Toast.makeText(SignUpActivity.this,responseString,Toast.LENGTH_SHORT).show();

    public void onSignUpFailed(String responseString) {
        Snackbar snack = Snackbar.make(relativeLayout, responseString, Snackbar.LENGTH_LONG);
        snack.show();


//        Snackbar snack = Snackbar.make(relative_signUp_root, responseString, Snackbar.LENGTH_LONG);
//        View view = snack.getView();
//        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
//        tv.setTextColor(getResources().getColor(R.color.white));
//        snack.show();
        //Toast.makeText(SignUpActivity.this,responseString,Toast.LENGTH_SHORT).show();

    }

}