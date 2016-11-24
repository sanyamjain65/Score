package mypocketvakil.example.com.score.NetworkCall;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import mypocketvakil.example.com.score.NetworkParser.NetworkParser;
import mypocketvakil.example.com.score.ResponseBean.ForgotResponseBean;
import mypocketvakil.example.com.score.ResponseBean.LoginResponseBean;
import mypocketvakil.example.com.score.ResponseBean.PostResponseBean;
import mypocketvakil.example.com.score.ResponseBean.ResetResponseBean;
import mypocketvakil.example.com.score.ResponseBean.SignUpResponseBean;

/**
 * Created by sanyam jain on 26-09-2016.
 */
public class NetworkCall {
    private static NetworkCall instance = null;

    int responseCode;
    private NetworkParser NetworkParser;

    private NetworkCall(Context context) {
        NetworkParser = new NetworkParser(context.getApplicationContext());
    }

    public static NetworkCall getInstance(Context context) {
        if (instance == null)
            instance = new NetworkCall(context);
        return instance;
    }

    public SignUpResponseBean signupData(String requestURL, HashMap<String, String> postDataParameters) {
        String result = performPostCall(requestURL, postDataParameters);
        Log.d("Response: ", "> " + result);

        return NetworkParser.parseSignUpData(result);
    }

    public ForgotResponseBean forgotData(String url, HashMap<String, String> postdataparams) {

        String result = performPostCall(url, postdataparams);
        Log.d("Response: ", "> " + result);

        return NetworkParser.parseForgotData(result);
    }

    public ResetResponseBean resetData(String url, HashMap postdataparams) {
        String result = performPostCall(url, postdataparams);
        Log.d("Response: ", "> " + result);

        return NetworkParser.parseResetData(result);

    }

    public LoginResponseBean loginData(String url, HashMap postdataparams) {
        String result = performPostCall(url, postdataparams);
        Log.d("Response: ", "> " + result);

        return NetworkParser.parseLoginData(result);

    }

    public PostResponseBean postData(String url, HashMap postdataparams) {
        String result = performPostCall(url, postdataparams);
        Log.d("Response: ", "> " + result);

        return NetworkParser.parsePostData(result);

    }

    private String performPostCall(String url, HashMap<String, String> postDataParams) {
        String response = "";
        try {
            URL url1;

            url1 = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setReadTimeout(25000);
            conn.setConnectTimeout(25000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(getPostDataString(postDataParams));
            wr.flush();
            wr.close();
            responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else if (responseCode == HttpURLConnection.HTTP_CREATED) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return response;


    }

    private String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));


        }
        return result.toString();
    }


}

