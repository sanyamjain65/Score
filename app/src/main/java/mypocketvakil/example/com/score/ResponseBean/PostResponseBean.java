package mypocketvakil.example.com.score.ResponseBean;

/**
 * Created by sanyam jain on 09-10-2016.
 */
public class PostResponseBean {
    public int ErrorCode;
    public String ResponseString;

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getResponseString() {
        return ResponseString;
    }

    public void setResponseString(String responseString) {
        ResponseString = responseString;
    }
}
