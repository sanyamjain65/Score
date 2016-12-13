package mypocketvakil.example.com.score.NetworkParser;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mypocketvakil.example.com.score.Preferences.SharedPreference;
import mypocketvakil.example.com.score.ResponseBean.AcceptResponseBean;
import mypocketvakil.example.com.score.ResponseBean.BidResponseBean;
import mypocketvakil.example.com.score.ResponseBean.ForgotResponseBean;
import mypocketvakil.example.com.score.ResponseBean.ImageResponseBean;
import mypocketvakil.example.com.score.ResponseBean.LoginResponseBean;
import mypocketvakil.example.com.score.ResponseBean.PostResponseBean;
import mypocketvakil.example.com.score.ResponseBean.ResetResponseBean;
import mypocketvakil.example.com.score.ResponseBean.SignUpResponseBean;

/**
 * Created by sanyam jain on 26-09-2016.
 */
public class NetworkParser {
    SharedPreference sharedPreference;
    private Context context;
    private int errorCode;
    private String responseString;

    public NetworkParser(Context context) {
        this.context = context;
    }


    public SignUpResponseBean parseSignUpData(String response) {

        SignUpResponseBean singUpResponseBean = new SignUpResponseBean();

        try {
            if (response != null) {
                JSONArray object = new JSONArray(response);
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
                    singUpResponseBean.setErrorCode(errorCode);
                    singUpResponseBean.setResponseString(responseString);

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
                    singUpResponseBean.setErrorCode(errorCode);
                    singUpResponseBean.setResponseString(responseString);


                }
            }
        } catch (JSONException e) {
            singUpResponseBean.setErrorCode(100);
            singUpResponseBean.setResponseString("Network Error");

            e.printStackTrace();

        }
        return singUpResponseBean;
    }

    public ForgotResponseBean parseForgotData(String response) {
        int id;
        ForgotResponseBean forgotresponsebean = new ForgotResponseBean();
        try {
            if (response != null) {
                JSONArray object = new JSONArray(response);
                JSONObject o = object.getJSONObject(0);

//                JSONObject object = new JSONObject(response);

                responseString = o.getString("response");
                errorCode = o.getInt("error");
                id = o.getInt("id");

                /*userId=obj.getInt(NetworkKeys.NET_KEY.USER_ID.KEY);
                user_email=obj.getString(NetworkKeys.NET_KEY.USER_EMAIL.KEY);
                user_full_name=obj.getString(NetworkKeys.NET_KEY.USER_FULL_NAME.KEY);
                access_token=obj.getString(NetworkKeys.NET_KEY.ACCESS_TOKEN.KEY);
*/
                if (errorCode == 0) {
                    forgotresponsebean.setId(id);
                    sharedPreference = SharedPreference.getInstance(context);
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("user_id", id);
                    editor.commit();
                    forgotresponsebean.setErrorCode(errorCode);
                    forgotresponsebean.setResponseString(responseString);

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
                    forgotresponsebean.setErrorCode(errorCode);
                    forgotresponsebean.setResponseString(responseString);


                }
            }
        } catch (JSONException e) {
            forgotresponsebean.setErrorCode(100);
            forgotresponsebean.setResponseString("Network Error");

            e.printStackTrace();

        }
        return forgotresponsebean;

    }

    public ResetResponseBean parseResetData(String response) {
        ResetResponseBean resetresponsebean = new ResetResponseBean();
        try {
            if (response != null) {
                JSONArray object = new JSONArray(response);
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
                    resetresponsebean.setErrorCode(errorCode);
                    resetresponsebean.setResponseString(responseString);

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
                    resetresponsebean.setErrorCode(errorCode);
                    resetresponsebean.setResponseString(responseString);


                }
            }
        } catch (JSONException e) {
            resetresponsebean.setErrorCode(100);
            resetresponsebean.setResponseString("Network Error");

            e.printStackTrace();

        }
        return resetresponsebean;

    }

    public LoginResponseBean parseLoginData(String response) {
        int id;
        LoginResponseBean loginresponsebean = new LoginResponseBean();
        try {
            if (response != null) {
                JSONArray object = new JSONArray(response);
                JSONObject o = object.getJSONObject(0);

//                JSONObject object = new JSONObject(response);

                responseString = o.getString("response");
                errorCode = o.getInt("error");
                id = o.getInt("id");

                /*userId=obj.getInt(NetworkKeys.NET_KEY.USER_ID.KEY);
                user_email=obj.getString(NetworkKeys.NET_KEY.USER_EMAIL.KEY);
                user_full_name=obj.getString(NetworkKeys.NET_KEY.USER_FULL_NAME.KEY);
                access_token=obj.getString(NetworkKeys.NET_KEY.ACCESS_TOKEN.KEY);
*/
                if (errorCode == 0) {
                    loginresponsebean.setId(id);
                    sharedPreference = SharedPreference.getInstance(context);
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("id", id);
                    editor.commit();


                    loginresponsebean.setErrorCode(errorCode);
                    loginresponsebean.setResponseString(responseString);

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
                    loginresponsebean.setId(id);
                    loginresponsebean.setErrorCode(errorCode);
                    loginresponsebean.setResponseString(responseString);


                }
            }
        } catch (JSONException e) {
            loginresponsebean.setErrorCode(100);
            loginresponsebean.setResponseString("Network Error");

            e.printStackTrace();

        }
        return loginresponsebean;
    }

    public PostResponseBean parsePostData(String response) {
        PostResponseBean postresponsebean = new PostResponseBean();
        try {
            if (response != null) {
                JSONArray object = new JSONArray(response);
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
                    postresponsebean.setErrorCode(errorCode);
                    postresponsebean.setResponseString(responseString);

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
                    postresponsebean.setErrorCode(errorCode);
                    postresponsebean.setResponseString(responseString);


                }
            }
        } catch (JSONException e) {
            postresponsebean.setErrorCode(100);
            postresponsebean.setResponseString("Network Error");

            e.printStackTrace();

        }
        return postresponsebean;

    }

    public BidResponseBean parseBidData(String response) {
        BidResponseBean bidresponsebean = new BidResponseBean();
        try {
            if (response != null) {
                JSONArray object = new JSONArray(response);
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
                    bidresponsebean.setErrorCode(errorCode);
                    bidresponsebean.setResponseString(responseString);

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
                    bidresponsebean.setErrorCode(errorCode);
                    bidresponsebean.setResponseString(responseString);


                }
            }
        } catch (JSONException e) {
            bidresponsebean.setErrorCode(100);
            bidresponsebean.setResponseString("Network Error");

            e.printStackTrace();

        }
        return bidresponsebean;

    }

    public AcceptResponseBean parseAcceptData(String response) {
        AcceptResponseBean acceptresponsebean = new AcceptResponseBean();
        try {
            if (response != null) {
                JSONArray object = new JSONArray(response);
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
                    acceptresponsebean.setErrorCode(errorCode);
                    acceptresponsebean.setResponseString(responseString);

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
                    acceptresponsebean.setErrorCode(errorCode);
                    acceptresponsebean.setResponseString(responseString);


                }
            }
        } catch (JSONException e) {
            acceptresponsebean.setErrorCode(100);
            acceptresponsebean.setResponseString("Network Error");

            e.printStackTrace();

        }
        return acceptresponsebean;
    }

    public ImageResponseBean parseimageData(String response) {
        ImageResponseBean imageresponsebean = new ImageResponseBean();
        try {
            if (response != null) {
                JSONArray object = new JSONArray(response);
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
                    imageresponsebean.setErrorCode(errorCode);
                    imageresponsebean.setResponseString(responseString);

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
                    imageresponsebean.setErrorCode(errorCode);
                    imageresponsebean.setResponseString(responseString);


                }
            }
        } catch (JSONException e) {
            imageresponsebean.setErrorCode(100);
            imageresponsebean.setResponseString("Network Error");

            e.printStackTrace();

        }
        return imageresponsebean;
    }
}
