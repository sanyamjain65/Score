package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.ResponseBean.PostResponseBean;
import mypocketvakil.example.com.score.activity.Post;


public class PostAsyncTask extends AsyncTask<PostResponseBean, PostResponseBean, PostResponseBean> {
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
        return NetworkCall.getInstance(context).postData(NetworkKeys.NET_KEY.POST_URL, postdataparams);
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
            ((Post) context).onSuccessfulSignUp(responseString);

        } else if (postResponseBean != null) {
            responseString = postResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Post)context).onSignUpFailed(responseString);
        } else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Post) context).onSignUpFailed("Network error");
        }
    }
}
