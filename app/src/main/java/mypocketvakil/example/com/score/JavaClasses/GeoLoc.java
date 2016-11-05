package mypocketvakil.example.com.score.JavaClasses;

/**
 * Created by sanyam jain on 07-10-2016.
 */
public class GeoLoc {
    private static GeoLoc instance = null;

    public double latitude;
    public double longitude;

    public static GeoLoc getInstance() {
        if (instance == null) {
            instance = new GeoLoc();
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
