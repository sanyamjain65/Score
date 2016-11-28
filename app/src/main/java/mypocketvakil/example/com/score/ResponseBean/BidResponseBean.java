package mypocketvakil.example.com.score.ResponseBean;

/**
 * Created by sanyam jain on 27-09-2016.
 */
public class BidResponseBean {
    public int ErrorCode;
    public String ResponseString;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
