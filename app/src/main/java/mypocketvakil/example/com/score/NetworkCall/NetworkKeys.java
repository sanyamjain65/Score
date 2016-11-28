package mypocketvakil.example.com.score.NetworkCall;

/**
 * Created by sanyam jain on 27-11-2016.
 */

public class NetworkKeys {
    public static enum NET_KEY {
        QUESTION("Question"),
        ANSWER("Answer"),
        RESPONSE_STRING("response_str"),
        ERROR_CODE("error_code"),
        RESULT("result"),
        USER_FULL_NAME("user_full_name"),
        USER_PASSWORD("user_password"),
        USER_ID("user_id"),
        USER_EMAIL("user_email"),
        USER_OTP(""),
        PASSWORD("password"),
        TOKEN_TYPE("token_type"),
        NAME("name"),
        DETAILS("details"),
        FORGOT_PASSWORD_URL("forgot_password_url"),
        ID("id"),
        ACCESS_TOKEN("access-token"),
        USER_DEVICE_TOKEN("userDeviceToken"),
        NETWORK_ERROR("Network error"),
        USER_DEVICE_TYPE("userDeviceType");




        public final String KEY;
        NET_KEY(String key) {
            this.KEY = key;
        }

        public static String BASE_URL = "http://172.16.101.109:138/";
        public static  String SIGNUP_URL=BASE_URL+"user/";
        public  static String LOGIN_IN_URL=BASE_URL+"login/";
        public static String FORGOT_PASSWORD = BASE_URL + "email/";
        public  static String RESET_PASSWORD_URL=BASE_URL+"reset/";
        public  static String POST_URL=BASE_URL+"post/";
        public  static String USER_LOCATION=BASE_URL+"userlocation/";
        public  static String BID=BASE_URL+"post_response/";
        public static String FAQ_URL=BASE_URL+"faq";
        public static String FAQ_URL_HINDI=BASE_URL+"faqhindi";
        public static String QUARY_URL=BASE_URL+"query";

    }
}
