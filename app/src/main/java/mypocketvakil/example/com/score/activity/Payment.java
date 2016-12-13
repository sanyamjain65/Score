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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.R;

public class Payment extends AppCompatActivity {

    String id,jsonStr;
    private static final String TAG = Payment.class.getSimpleName();
    ArrayList<HashMap<String, String>> contactlist;
    ProgressBar progressBar;
    ListView payment_listview;
    TextView tv_payement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar=(Toolbar)findViewById(R.id.payment_toolbar);
        TextView tv_toolbar=(TextView) toolbar.findViewById(R.id.tv_toolbar);
        tv_toolbar.setText(R.string.transaction);
        progressBar=(ProgressBar)findViewById(R.id.payment_progressBar);
        payment_listview=(ListView)findViewById(R.id.payment_listview);
        tv_payement=(TextView)findViewById(R.id.tv_payment);
        contactlist = new ArrayList<>();
        new getContacts().execute();
    }

    @Override
    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Payment.this,user.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 100);
    }
    private class getContacts extends AsyncTask<Void, Void, Void> {
        String credits;



        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Payment.this);

            id = String.valueOf(sharedPref.getInt("id", -1));
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(NetworkKeys.NET_KEY.PASSBOOK+id+"/");
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);

                    for (int i = 0; i < contacts.length(); i++) {
                        if(i==0)
                        {
                            JSONObject c = contacts.getJSONObject(i);
                             credits=c.getString("credits");
                        }
                        else{
                            JSONObject c = contacts.getJSONObject(i);
                            String title=c.getString("work");
                            String name=c.getString("name");
                            String bid=c.getString("payment");




                            HashMap<String,String> contact=new HashMap<>();
                            contact.put("title", title);
                            contact.put("name", name);
                            contact.put("payment", bid);

                            contactlist.add(contact);
                        }



                    }

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
            tv_payement.setText(credits);
            ListAdapter listAdapter = new SimpleAdapter(Payment.this, contactlist, R.layout.payment_list, new String[]{"title","name","payment"}, new int[]{R.id.tv_payment_title,R.id.tv_payment_name,R.id.tv_payment_budget});
            payment_listview.setAdapter(listAdapter);
        }

    }
}
