package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.fragment.get;

/**
 * Created by sanyam jain on 09-10-2016.
 */
public class GetAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = GetAsyncTask.class.getSimpleName();
    private static final String url = "http://172.16.101.109:138/post/";
    ProgressDialog progressDialog;
    String jsonStr;
    ArrayList<HashMap<String, String>> contactlist;
    get g;
    Context context;

    public GetAsyncTask(get g, Context context) {
        this.context = context;
        this.g = g;
    }
//
//    public GetAsyncTask(Context context) {
//        this.context=context;
//    }


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

                    String title = c.getString("title");
                    String summary = c.getString("summary");
                    String budget = c.getString("budget");


                    HashMap<String, String> contact = new HashMap<>();
                    contact.put("title", title);
                    contact.put("summary", summary);
                    contact.put("budget", budget);

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
        progressDialog = new ProgressDialog(context);
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
        if (jsonStr != null) {
//            ((Info_wall)context).onSuccessful(contactlist);

        }

    }
}
