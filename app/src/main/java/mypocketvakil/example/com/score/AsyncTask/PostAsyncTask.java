package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.ResponseBean.PostResponseBean;
import mypocketvakil.example.com.score.activity.Location;

/**
 * Created by sanyam jain on 09-10-2016.
 */
public class PostAsyncTask extends AsyncTask<PostResponseBean, PostResponseBean, PostResponseBean> {
    private static final String url = "http://192.168.43.160:138/post/";
    ProgressDialog progressDialog;
    private HashMap postdataparams;
    private Context context;
    private String responseString;

    public PostAsyncTask(Context context, HashMap<String, String> postdataparams) {
        this.context = context;
        this.postdataparams = postdataparams;
    }

    @Override
    protected PostResponseBean doInBackground(PostResponseBean... params) {
        return NetworkCall.getInstance(context).postData(url, postdataparams);
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
    protected void onPostExecute(PostResponseBean postResponseBean) {
        super.onPostExecute(postResponseBean);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (postResponseBean != null && postResponseBean.getErrorCode() == 0) {
            responseString = postResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Location) context).onSuccessfulSignUp(responseString);

        } else if (postResponseBean != null) {
            responseString = postResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Location) context).onSignUpFailed(responseString);
        } else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Location) context).onSignUpFailed("Network error");
        }
    }
}
