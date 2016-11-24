package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.ResponseBean.ForgotResponseBean;
import mypocketvakil.example.com.score.activity.Forgot;

/**
 * Created by sanyam jain on 27-09-2016.
 */
public class ForgotAsyncTask extends AsyncTask<ForgotResponseBean, ForgotResponseBean, ForgotResponseBean> {
    private static final String url = "http://172.16.101.109:138/email/";
    ProgressDialog progressDialog;
    private HashMap postdataparams;
    private Context context;
    private String responseString;


    public ForgotAsyncTask(Context context, HashMap<String, String> postdataparams) {
        this.context = context;
        this.postdataparams = postdataparams;

    }

    @Override
    protected ForgotResponseBean doInBackground(ForgotResponseBean... params) {
        return NetworkCall.getInstance(context).forgotData(url, postdataparams);

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
    protected void onPostExecute(ForgotResponseBean forgotResponseBean) {
        super.onPostExecute(forgotResponseBean);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (forgotResponseBean != null && forgotResponseBean.getErrorCode() == 0) {
            responseString = forgotResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Forgot) context).onSuccessfulSignUp(responseString);

        } else if (forgotResponseBean != null) {
            responseString = forgotResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Forgot) context).onSignUpFailed(responseString);
        } else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Forgot) context).onSignUpFailed("Network error");
        }
    }
}
