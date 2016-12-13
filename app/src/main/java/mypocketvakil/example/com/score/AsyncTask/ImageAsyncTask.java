package mypocketvakil.example.com.score.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.score.NetworkCall.NetworkCall;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.ResponseBean.ImageResponseBean;
import mypocketvakil.example.com.score.activity.user;

/**
 * Created by sanyam jain on 04-12-2016.
 */

public class ImageAsyncTask extends AsyncTask<ImageResponseBean,ImageResponseBean,ImageResponseBean> {
    ProgressDialog progressDialog;
    private HashMap postdataparams;
    private Context context;
    private String responseString;
    String id;
    public ImageAsyncTask(Context context, HashMap<String, String> postdataparams,String id) {
        this.context = context;
        this.postdataparams = postdataparams;
        this.id= id;
    }

    @Override
    protected ImageResponseBean doInBackground(ImageResponseBean... imageResponseBeen) {
        return NetworkCall.getInstance(context).imageData(NetworkKeys.NET_KEY.ACCOUNT+id+"/", postdataparams);
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
    protected void onPostExecute(ImageResponseBean imageResponseBean) {
        super.onPostExecute(imageResponseBean);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (imageResponseBean != null && imageResponseBean.getErrorCode() == 0) {
            responseString = imageResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((user) context).onSuccessfulSignUp(responseString);

        } else if (imageResponseBean != null) {
            responseString = imageResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((user) context).onSignUpFailed(responseString);
        } else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((user) context).onSignUpFailed("Network error");
        }
    }
}
