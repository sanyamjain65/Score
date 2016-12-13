package mypocketvakil.example.com.score.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.Adapters.WorkPostedAdapter;
import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.R;

public class work_posted extends AppCompatActivity {
    String jsonStr;
    private static final String TAG = work_posted.class.getSimpleName();
    ListView work_listview;
    String id;
    ArrayList<HashMap<String, String>> contactlist;
    ProgressBar progressBar;
    String[] id1=new String[20];
    WorkPostedAdapter workPostedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_posted);
        work_listview=(ListView)findViewById(R.id.work_listview);
        contactlist = new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.work_progressBar);

        work_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(work_posted.this,Bid_Posted.class);
                        intent.putExtra("id",id1[i]);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                },100);


            }
        });
        new postUser().execute();

    }
    @Override
    public void onBackPressed() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(work_posted.this,user.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 100);
    }
    private class postUser extends AsyncTask<Void, Void, Void> {
        String img;
        Bitmap decodebitmap;



        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(work_posted.this);

            id = String.valueOf(sharedPref.getInt("id", -1));
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(NetworkKeys.NET_KEY.POST_USER+id+"/");
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String title=c.getString("work");
                        String image=c.getString("image");
                        id1[i]=c.getString("id");
                        if(image.equals("no image")){
                            img="no image";

                        }
                        else{
                            img=image;

//                            imageView.setImageBitmap(decodebitmap);
                        }

                        HashMap<String,String> contact=new HashMap<>();
                        contact.put("title", title);
                        contact.put("image", img);

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
//            ListAdapter listAdapter = new SimpleAdapter(work_posted.this, contactlist, R.layout.work_list_items, new String[]{"title","image"}, new int[]{R.id.tv_list_title,R.id.iv_work_list});
//            work_listview.setAdapter(listAdapter);
            workPostedAdapter=new WorkPostedAdapter(work_posted.this, contactlist);
            work_listview.setAdapter(workPostedAdapter);
        }

    }
}
