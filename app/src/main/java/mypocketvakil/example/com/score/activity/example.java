package mypocketvakil.example.com.score.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.R;

public class example extends AppCompatActivity {
    ImageView imageView;
    private static final String url = "http://172.16.101.109:138/post/";
    ProgressDialog progressDialog;
    private static final String TAG = Info_wall.class.getSimpleName();
    String jsonStr;
    ArrayList<HashMap<String, String>> contactlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        contactlist = new ArrayList<>();
        imageView=(ImageView)findViewById(R.id.iv_example);
        new getContacts().execute();
    }
    private class getContacts extends AsyncTask<Void, Void, Void> {
        String image;
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        image = c.getString("image");






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
            progressDialog = new ProgressDialog(example.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            byte[] decode= Base64.decode(image,Base64.DEFAULT);
            Bitmap decodebitmap= BitmapFactory.decodeByteArray(decode,0,decode.length);
            imageView.setImageBitmap(decodebitmap);

        }

    }
}
