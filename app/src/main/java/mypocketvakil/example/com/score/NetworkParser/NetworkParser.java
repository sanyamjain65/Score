package mypocketvakil.example.com.score.NetworkParser;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mypocketvakil.example.com.score.ResponseBean.ForgotResponseBean;
import mypocketvakil.example.com.score.ResponseBean.LoginResponseBean;
import mypocketvakil.example.com.score.ResponseBean.ResetResponseBean;
import mypocketvakil.example.com.score.ResponseBean.SignUpResponseBean;
import mypocketvakil.example.com.score.activity.Login;

/**
 * Created by sanyam jain on 26-09-2016.
 */
public class NetworkParser {
    private Context context;
    private int errorCode;
    private String responseString;
    public NetworkParser(Context context) {
        this.context = context;
    }


    public  SignUpResponseBean parseSignUpData(String response) {

        SignUpResponseBean singUpResponseBean = new SignUpResponseBean();

        try {
            if (response!=null) {
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
        ForgotResponseBean forgotresponsebean=new ForgotResponseBean();
        try {
            if (response!=null) {
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
        ResetResponseBean resetresponsebean=new ResetResponseBean();
        try {
            if (response!=null) {
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
        LoginResponseBean loginresponsebean=new LoginResponseBean();
        try {
            if (response!=null) {
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
}
