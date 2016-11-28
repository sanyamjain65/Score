package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.ResponseBean.BidResponseBean;
import mypocketvakil.example.com.score.activity.Info_wall;

/**
 * Created by sanyam jain on 28-11-2016.
 */

public class BidAsyncTask extends AsyncTask<BidResponseBean,BidResponseBean,BidResponseBean> {
    ProgressDialog progressDialog;
    HashMap postdataparams;
    Context context;
    private String responseString;
    String id;

    public BidAsyncTask(Context context, HashMap<String, String> postdataparams,String id) {
        this.context = context;
        this.postdataparams = postdataparams;
        this.id=id;
    }

    @Override
    protected BidResponseBean doInBackground(BidResponseBean... bidResponseBeen) {
        return NetworkCall.getInstance(context).bidData(NetworkKeys.NET_KEY.BID+id+"/", postdataparams);
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
    protected void onPostExecute(BidResponseBean bidResponseBean) {
        super.onPostExecute(bidResponseBean);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (bidResponseBean != null && bidResponseBean.getErrorCode() == 0) {
            responseString = bidResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Info_wall) context).onSuccessfulSignUp(responseString);

        } else if (bidResponseBean != null) {
            responseString = bidResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Info_wall) context).onSignUpFailed(responseString);
        } else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Info_wall) context).onSignUpFailed("Network error");
        }
    }
}
