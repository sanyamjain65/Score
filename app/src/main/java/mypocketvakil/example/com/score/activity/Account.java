package mypocketvakil.example.com.score.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.R;

public class Account extends AppCompatActivity {
    String id,jsonStr;
    private static final String TAG = Account.class.getSimpleName();
    ProgressBar progressBar;
    TextView tv_account_gender,tv_account_mail,tv_account_number,tv_account_dob;
    TextView tv_toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        progressBar=(ProgressBar)findViewById(R.id.account_progressBar);
        Toolbar toolbar=(Toolbar)findViewById(R.id.account_toolbar);
        tv_toolbar=(TextView) toolbar.findViewById(R.id.tv_toolbar);
        tv_account_gender=(TextView)findViewById(R.id.tv_account_gender);
        tv_account_mail=(TextView)findViewById(R.id.tv_account_mail);
        tv_account_number=(TextView)findViewById(R.id.tv_account_number);
        tv_account_dob=(TextView)findViewById(R.id.tv_account_dob);



        new getContacts().execute();
    }

    @Override
    public void onBackPressed() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Account.this,user.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 100);
    }
    private class getContacts extends AsyncTask<Void, Void, Void> {
        String name,gender,email,dob,number;



        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Account.this);

            id = String.valueOf(sharedPref.getInt("id", -1));
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(NetworkKeys.NET_KEY.ACCOUNT+id+"/");
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);
                    JSONObject c = contacts.getJSONObject(0);
                    name=c.getString("name");
                    dob=c.getString("dob");
                    gender=c.getString("gender");
                    email=c.getString("email");
                    number=c.getString("mobile_no");


                } catch (Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }

            tv_toolbar.setText(name);
            tv_account_mail.setText(email);
            tv_account_dob.setText(dob);
            tv_account_number.setText(number);
            tv_account_gender.setText(gender);

        }

    }
}
