package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.ResponseBean.SignUpResponseBean;
import mypocketvakil.example.com.score.activity.Sign_up;

/**
 * Created by sanyam jain on 25-09-2016.
 */
public class SignUpAsyncTask extends AsyncTask<SignUpResponseBean, SignUpResponseBean, SignUpResponseBean> {
    private static final String url = "http://172.16.101.109:138/user/";
    ProgressDialog progressDialog;
    private HashMap<String, String> postdataparams;
    private Context context;

    private String responseString;


    public SignUpAsyncTask(Context context, HashMap<String, String> postdataparams) {
        this.context = context;
        this.postdataparams = postdataparams;
    }


    @Override
    protected SignUpResponseBean doInBackground(SignUpResponseBean... params) {
        return NetworkCall.getInstance(context).signupData(url, postdataparams);
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
    protected void onPostExecute(SignUpResponseBean signUpResponseBean) {
        super.onPostExecute(signUpResponseBean);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (signUpResponseBean != null && signUpResponseBean.getErrorCode() == 0) {
            responseString = signUpResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Sign_up) context).onSuccessfulSignUp(responseString);

        } else if (signUpResponseBean != null) {
            responseString = signUpResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Sign_up) context).onSignUpFailed(responseString);
        } else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Sign_up) context).onSignUpFailed("Network error");
        }
    }
}
