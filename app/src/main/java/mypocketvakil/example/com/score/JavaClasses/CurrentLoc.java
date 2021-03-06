package mypocketvakil.example.com.score.JavaClasses;

/**
 * Created by sanyam jain on 26-11-2016.
 */
public class CurrentLoc {
    private static CurrentLoc instance = null;

    public double latitude;
    public double longitude;

    public static CurrentLoc getInstance() {
        if (instance == null) {
            instance = new CurrentLoc();
        }
        return instance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
