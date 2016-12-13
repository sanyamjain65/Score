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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.Adapters.MyBidAdapter;
import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.R;

public class My_Bids extends AppCompatActivity {
    String id,jsonStr;
    private static final String TAG = My_Bids.class.getSimpleName();
    ArrayList<HashMap<String, String>> contactlist;
    ProgressBar progressBar;
    ListView mybids_listview;
    MyBidAdapter bidAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__bids);
        Toolbar toolbar=(Toolbar)findViewById(R.id.mybids_toolbar);
        TextView tv_toolbar=(TextView) toolbar.findViewById(R.id.tv_toolbar);
        tv_toolbar.setText(R.string.mybids);
        progressBar=(ProgressBar)findViewById(R.id.mybids_progressBar);
        mybids_listview=(ListView)findViewById(R.id.mybids_listview);
        contactlist = new ArrayList<>();
        new getContacts().execute();

    }

    @Override
    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(My_Bids.this,user.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 100);
    }

    private class getContacts extends AsyncTask<Void, Void, Void> {


        String img;

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(My_Bids.this);

            id = String.valueOf(sharedPref.getInt("id", -1));
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(NetworkKeys.NET_KEY.MY_BIDS+id+"/");
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);

                    for (int i = 0; i < contacts.length(); i++) {
                        String stat;
                        JSONObject c = contacts.getJSONObject(i);
                        String title=c.getString("work");
                        String min=c.getString("budget_min");
                        String max=c.getString("budget_max");
                        String status=c.getString("status");
                        String bid=c.getString("bid");
                        String image=c.getString("image");
                        if(image.equals("no image")){
                            img="no image";

                        }
                        else{
                            img=image;

//                            imageView.setImageBitmap(decodebitmap);
                        }
                        if(status.equals("-1"))
                        {
                            stat="Rejected";

                        }
                        else if(status.equals("0"))
                        {
                            stat="Waiting";
                        }
                        else
                        {
                            stat="Confirmed";
                        }


                        HashMap<String,String> contact=new HashMap<>();
                        contact.put("title", title);
                        contact.put("budget_min", min);
                        contact.put("budget_max", max);
                        contact.put("status", stat);
                        contact.put("bid", bid);
                        contact.put("image",image);

                        contactlist.add(contact);
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
//            ListAdapter listAdapter = new SimpleAdapter(My_Bids.this, contactlist, R.layout.mybids_list, new String[]{"title","budget_min","budget_max","status","bid"}, new int[]{R.id.tv_mybids_title,R.id.tv_mybidbid_min,R.id.tv_mybid_max,R.id.tv_mybid_status,R.id.tv_mybid_budget});
//            mybids_listview.setAdapter(listAdapter);
            bidAdapter=new MyBidAdapter(My_Bids.this, contactlist);
            mybids_listview.setAdapter(bidAdapter);
        }

    }
}
