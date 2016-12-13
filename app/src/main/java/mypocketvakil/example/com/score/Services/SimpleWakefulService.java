package mypocketvakil.example.com.score.Services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.github.akashandroid90.googlesupport.location.AppLocationIntentService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import mypocketvakil.example.com.score.JavaClasses.CurrentLoc;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;

/**
 * Created by sanyam jain on 26-11-2016.
 */

public class SimpleWakefulService extends AppLocationIntentService {

    CurrentLoc currentLoc=new CurrentLoc().getInstance();
    int responseCode;

    String user_id="24";
    private int errorCode;
    private String responseString;
    int ErrorCode;
    String ResponseString;
    String lat,lng,id;
    HashMap<String,String> postdataparams;
    double latitude,longitude,lati,longi;

    public SimpleWakefulService() {
        super("MyService");
    }

    @Override
    public void myCurrentLocation(Location currentLocation) {
        super.myCurrentLocation(currentLocation);
        latitude=currentLocation.getLatitude();
        longitude=currentLocation.getLongitude();
        loc(latitude,longitude);




//        loc(latitude,longitude);
    }
    public CurrentLoc loc(double latitude, double longitude) {


        CurrentLoc g = new CurrentLoc().getInstance();
        g.setLatitude(latitude);
        g.setLongitude(longitude);
        return g;


    }

    private void parseLocData(String result) {
        try {
            if (result != null) {
                JSONArray object = new JSONArray(result);
                JSONObject o = object.getJSONObject(0);

//                JSONObject object = new JSONObject(response);

                responseString = o.getString("response");
                errorCode = o.getInt("error");

                /*userId=obj.getInt(NetworkKeys.NET_KEY.USER_ID.KEY);
                user_email=obj.getString(NetworkKeys.NET_KEY.USER_EMAIL.KEY);
                user_full_name=obj.getString(NetworkKeys.NET_KEY.USER_FULL_NAME.KEY);
                access_token=obj.getString(NetworkKeys.NET_KEY.ACCESS_TOKEN.KEY);
*/

                if (errorCode == 0) {
                    ErrorCode=errorCode;
                    ResponseString=responseString;

                   /* "access_token": "w3HPKOodd6qzmvu7aScjJVs3On0ZsgqDvMTJtdUs",
                            "token_type": "Bearer",
                            "expires_in": 36000,
                            "partners": {
                        "id": "1"
                    /*String access_token = object.getString(NetworkKeys.NET_KEY.ACCESS_TOKEN.KEY);
                    String token_type = object.getString(NetworkKeys.NET_KEY.TOKEN_TYPE.KEY);
                    JSONObject partners = object.getJSONObject(NetworkKeys.NET_KEY.USER_DEVICE_TOKEN.KEY);
                    String id = object.getString(NetworkKeys.NET_KEY.ID.KEY);

                    loginResponseBean.setAccess_token(access_token);
                    loginResponseBean.setId(id);
                    loginResponseBean.setToken_type(token_type);*/
//                    loginResponseBean.setUser(deviceToken);

                } else {
                    ErrorCode=errorCode;
                    ResponseString=responseString;


                }
            }
        } catch (JSONException e) {
            ErrorCode=100;
            ResponseString="Network Error";

            e.printStackTrace();

        }

    }

    private String performPostCall(String url, HashMap<String, String> postDataParams) {
        String response = "";
        try {
            URL url1;

            url1 = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setReadTimeout(25000);
            conn.setConnectTimeout(25000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(getPostDataString(postDataParams));
            wr.flush();
            wr.close();
            responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
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

    @Override
    protected void onHandleIntent(Intent intent) {
        lati=currentLoc.getLatitude();
        longi=currentLoc.getLongitude();
        if (lati==0.0 || longi==0.0)
        {

        }
        else {
            try {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SimpleWakefulService.this);

                id = String.valueOf(sharedPref.getInt("id", -1));
                postdataparams = new HashMap<String, String>();
                lat = String.valueOf(lati);
                lng = String.valueOf(longi);

                postdataparams.put("user_id", id);
                postdataparams.put("latitude", lat);
                postdataparams.put("longitude", lng);
                String result = performPostCall(NetworkKeys.NET_KEY.USER_LOCATION+id+"/", postdataparams);
                parseLocData(result);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "service not started", Toast.LENGTH_LONG).show();
            }
        }

    }
}

