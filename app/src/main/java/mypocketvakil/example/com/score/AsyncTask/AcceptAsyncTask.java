package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.ResponseBean.AcceptResponseBean;
import mypocketvakil.example.com.score.activity.Bid_Posted;

/**
 * Created by sanyam jain on 04-12-2016.
 */

public class AcceptAsyncTask extends AsyncTask<AcceptResponseBean,AcceptResponseBean,AcceptResponseBean> {
    ProgressDialog progressDialog;
    private HashMap postdataparams;
    private Context context;
    private String responseString;
    String id;

    public AcceptAsyncTask(Context context, HashMap<String, String> postdataparams, String id) {
        this.context = context;
        this.postdataparams = postdataparams;
        this.id=id;
    }

    @Override
    protected AcceptResponseBean doInBackground(AcceptResponseBean... acceptResponseBeen) {
        return NetworkCall.getInstance(context).acceptData(NetworkKeys.NET_KEY.STATUS+id+"/", postdataparams);
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
    protected void onPostExecute(AcceptResponseBean acceptResponseBean) {
        super.onPostExecute(acceptResponseBean);

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (acceptResponseBean != null && acceptResponseBean.getErrorCode() == 0) {
            responseString = acceptResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Bid_Posted) context).onSuccessfulSignUp(responseString);

        } else if (acceptResponseBean != null) {
            responseString = acceptResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Bid_Posted) context).onSignUpFailed(responseString);
        } else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Bid_Posted) context).onSignUpFailed("Network error");
        }
    }
}
