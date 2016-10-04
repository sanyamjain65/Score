package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.ResponseBean.ResetResponseBean;
import mypocketvakil.example.com.score.activity.Forgot;
import mypocketvakil.example.com.score.activity.Reset_password;

/**
 * Created by sanyam jain on 27-09-2016.
 */
public class ResetAsyncTask extends AsyncTask<ResetResponseBean,ResetResponseBean,ResetResponseBean> {
    private HashMap postdataparams;
    private Context context;
    ProgressDialog progressDialog;
    private String responseString;

    private  static final String url="http://172.16.107.146:138/reset/";
    public ResetAsyncTask(Context context,HashMap<String,String> postdataparams)
    {
        this.context=context;
        this.postdataparams=postdataparams;

    }
    @Override
    protected ResetResponseBean doInBackground(ResetResponseBean... params) {
        return NetworkCall.getInstance(context).resetData(url,postdataparams);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ResetResponseBean resetResponseBean) {
        super.onPostExecute(resetResponseBean);
        if(progressDialog!=null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
        if (resetResponseBean !=null && resetResponseBean.getErrorCode()==0 ) {
            responseString = resetResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Reset_password)context).onSuccessfulSignUp(responseString);

        }
        else if (resetResponseBean !=null) {
            responseString = resetResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Reset_password)context).onSignUpFailed(responseString);
        }
        else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Reset_password)context).onSignUpFailed("Network error");
        }
    }
}
