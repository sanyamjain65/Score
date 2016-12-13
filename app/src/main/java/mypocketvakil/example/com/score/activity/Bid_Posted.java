package mypocketvakil.example.com.score.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.Adapters.BidPostedAdapter;
import mypocketvakil.example.com.score.AsyncTask.AcceptAsyncTask;
import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.R;

public class Bid_Posted extends AppCompatActivity {

    String jsonStr;
    private static final String TAG = Bid_Posted.class.getSimpleName();
    ListView workbid_listview;
    RelativeLayout activity_bid_posted;
    String id,user_id,yes,no;
    String[] bid_id=new String[20];
    ArrayList<HashMap<String, String>> contactlist;
    HashMap<String,String> postdataparams;
    ProgressBar progressBar;
    TextView tv_workbid_title,tv_workbid_detail,tv_workbid_min,tv_workbid_max,tv_accept,tv_workbid_delivered;
    int count=0;
    String status="1";
    String position,img;
    BidPostedAdapter bidposted;
    ImageView iv_work_bid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid__posted);
        workbid_listview=(ListView)findViewById(R.id.work_bid_listview);
        contactlist = new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.workbid_progressBar);
        tv_workbid_title=(TextView)findViewById(R.id.tv_workbid_title);
        tv_workbid_detail=(TextView)findViewById(R.id.tv_workbid_detail);
        tv_workbid_min=(TextView)findViewById(R.id.tv_workbid_min);
        tv_workbid_max=(TextView)findViewById(R.id.tv_workbid_max);
        activity_bid_posted=(RelativeLayout)findViewById(R.id.activity_bid__posted);
        tv_workbid_delivered=(TextView)findViewById(R.id.tv_workbid_delivered);
        iv_work_bid=(ImageView)findViewById(R.id.iv_work_bid);
        tv_workbid_delivered.setVisibility(View.GONE);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        new bids().execute();
        tv_workbid_delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Bid_Posted.this);
                builder1.setCancelable(true);
                builder1.setMessage("This action cannot be reverted back. Are you your item is deivered?");
                builder1.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new getDelivery().execute();


                    }
                });
                builder1.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog fMapTypeDialog1 = builder1.create();
                fMapTypeDialog1.setCanceledOnTouchOutside(true);
                fMapTypeDialog1.show();
            }
        });
        workbid_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int pos, long l) {
                if (count==0)
                {


                    position=bid_id[pos];
                    AlertDialog.Builder builder = new AlertDialog.Builder(Bid_Posted.this);
                    builder.setCancelable(true);
                    builder.setMessage("Once selected it cannot be undone. Do hyou want to select this Bid?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tv_accept=(TextView)view.findViewById(R.id.tv_accept);
                            postdataparams=new HashMap<String, String>();
                            postdataparams.put("status", status);
                            AcceptAsyncTask task=new AcceptAsyncTask(Bid_Posted.this,postdataparams,bid_id[pos]);
                            task.execute();
                            tv_accept.setText(R.string.confirmed);
                            tv_accept.setTextColor(R.color.colorAccent);
                            tv_workbid_delivered.setVisibility(View.VISIBLE);
                            count=count+1;
                        }
                    });
                    builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog fMapTypeDialog = builder.create();
                    fMapTypeDialog.setCanceledOnTouchOutside(true);
                    fMapTypeDialog.show();

                }
                else
                {
                    Snackbar snack=Snackbar.make(activity_bid_posted,"You cannot accept more than one bid",Snackbar.LENGTH_LONG);
                    snack.show();
                }



            }
        });
    }

    public void onSuccessfulSignUp(String responseString) {
        Snackbar snack=Snackbar.make(activity_bid_posted,"Bid has been Confirmed",Snackbar.LENGTH_LONG);
        snack.show();

    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack=Snackbar.make(activity_bid_posted,responseString,Snackbar.LENGTH_LONG);
        snack.show();
    }
    @Override
    public void onBackPressed() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Bid_Posted.this,work_posted.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 100);
    }

    private class bids extends AsyncTask<Void, Void, Void> {
        String work,details,min,max,image1;


        @Override
        protected Void doInBackground(Void... params) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Bid_Posted.this);

            user_id = String.valueOf(sharedPref.getInt("id", -1));
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(NetworkKeys.NET_KEY.POST_RESPONSE+id+"/");
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);

                    for (int i = 0; i < contacts.length(); i++) {
                        if(i==0)
                        {
                            JSONObject c = contacts.getJSONObject(i);
                            work=c.getString("work");
                            details=c.getString("details");
                            min=c.getString("budget_min");
                            max=c.getString("budget_max");
                            image1=c.getString("image");


                        }
                        else{
                            JSONObject c1 = contacts.getJSONObject(i);
                            String name=c1.getString("name");
                            String bid=c1.getString("bid");
                            bid_id[i-1]=c1.getString("id");
                            String image=c1.getString("image");
                            if(image.equals("no image")){
                                img="no image";

                            }
                            else{
                                img=image;

//                            imageView.setImageBitmap(decodebitmap);
                            }




                            HashMap<String,String> contact=new HashMap<>();
                            contact.put("name",name);
                            contact.put("bid",bid);
                            contact.put("image",img);
                            

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
            tv_workbid_title.setText(work);
            tv_workbid_detail.setText(details);
            tv_workbid_min.setText(min);
            tv_workbid_max.setText(max);
            byte[] decode= Base64.decode(image1,Base64.DEFAULT);
            Bitmap decodebitmap= BitmapFactory.decodeByteArray(decode,0,decode.length);


            Bitmap circleBitmap = Bitmap.createBitmap(decodebitmap.getWidth(), decodebitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Rect rect = new Rect(0, 0, decodebitmap.getWidth(), decodebitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = 1000;

            BitmapShader shader = new BitmapShader(decodebitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);
            paint.setAntiAlias(true);
            Canvas c = new Canvas(circleBitmap);
            c.drawRoundRect(rectF, roundPx, roundPx, paint);
            iv_work_bid.setImageBitmap(circleBitmap);


//            ListAdapter listAdapter = new SimpleAdapter(Bid_Posted.this, contactlist, R.layout.work_bid, new String[]{"name","bid"}, new int[]{R.id.tv_workbidlist_name,R.id.tv_workbidlist_budget});
//            workbid_listview.setAdapter(listAdapter);
            bidposted=new BidPostedAdapter(Bid_Posted.this, contactlist);
            workbid_listview.setAdapter(bidposted);
        }

    }

    private class getDelivery extends AsyncTask<Void, Void, Void> {
String response,error;


        @Override
        protected Void doInBackground(Void... params) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Bid_Posted.this);

            user_id = String.valueOf(sharedPref.getInt("id", -1));
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(NetworkKeys.NET_KEY.PAYMENT+position+"/");
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);
                    JSONObject c = contacts.getJSONObject(0);
                     response=c.getString("response");
                     error=c.getString("error");


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
            Snackbar snack=Snackbar.make(activity_bid_posted,response,Snackbar.LENGTH_LONG);
            snack.show();

        }

    }
}
