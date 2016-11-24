package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.ResponseBean.LoginResponseBean;
import mypocketvakil.example.com.score.activity.Login;

/**
 * Created by sanyam jain on 27-09-2016.
 */
public class LoginAsyncTask extends AsyncTask<LoginResponseBean, LoginResponseBean, LoginResponseBean> {

    private static final String url = "http://172.16.101.109:138/login/";
    ProgressDialog progressDialog;
    private HashMap postdataparams;
    private Context context;
    private String responseString;


    public LoginAsyncTask(Context context, HashMap<String, String> postdataparams) {
        this.context = context;
        this.postdataparams = postdataparams;
    }

    @Override
    protected LoginResponseBean doInBackground(LoginResponseBean... params) {
        return NetworkCall.getInstance(context).loginData(url, postdataparams);
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
    protected void onPostExecute(LoginResponseBean loginResponseBean) {
        super.onPostExecute(loginResponseBean);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (loginResponseBean != null && loginResponseBean.getErrorCode() == 0) {

            responseString = loginResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Login) context).onSuccessfulSignUp(responseString);

        } else if (loginResponseBean != null) {
            responseString = loginResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Login) context).onSignUpFailed(responseString);
        } else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Login) context).onSignUpFailed("Network error");
        }

    }
}
