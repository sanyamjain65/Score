package mypocketvakil.example.com.score.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import mypocketvakil.example.com.score.AsyncTask.PostAsyncTask;
import mypocketvakil.example.com.score.JavaClasses.GeoLoc;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.fragment.post;

public class Location extends FragmentActivity implements LocationListener, OnMapReadyCallback, PlaceSelectionListener {
    GeoLoc loc = new GeoLoc().getInstance();
    double lat, latitude;
    double lng, longitude;
    post p = new post();
    GoogleMap googlemap;
    Marker marker[] = new Marker[2];
    TextView help;
    ImageView iv_help;
    boolean message = false;
    String title, summary, description, budget, time;
    HashMap<String, String> postdataparams;
    RelativeLayout rl_loc;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        boolean lowPowerMoreImportantThanAccurancy = true;
        LocationRequest request = LocationRequest.create();
        request.setPriority(lowPowerMoreImportantThanAccurancy ?
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY :
                LocationRequest.PRIORITY_HIGH_ACCURACY);

//        SupportMapFragment map = ((SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map));

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        summary = intent.getStringExtra("summary");
        description = intent.getStringExtra("description");
        budget = intent.getStringExtra("budget");
        time = intent.getStringExtra("duration");

        rl_loc = (RelativeLayout) findViewById(R.id.rl_loc);
        help = (TextView) findViewById(R.id.tv_map_help);
        help.setVisibility(View.GONE);
        iv_help = (ImageView) findViewById(R.id.iv_map_question);
        iv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message) {
                    message = false;
                    help.setVisibility(View.GONE);
                } else {
                    message = true;
                    help.setVisibility(View.VISIBLE);
                }

            }
        });
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(Location.this);

        View mapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getView();
        View btnMyLocation = ((View) mapView.findViewById(1).getParent()).findViewById(2);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100); // size of button in dp
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.setMargins(600, 800, 10, 0);
        btnMyLocation.setLayoutParams(params);


        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.getView().setBackgroundColor(Color.WHITE);
        autocompleteFragment.setOnPlaceSelectedListener(Location.this);


        lat = loc.getLatitude();
        lng = loc.getLongitude();


    }

    @Override
    public void onMapReady(final GoogleMap map) {
        googlemap = map;

        final LatLng latLng = new LatLng(lat, lng);
        marker[0] = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Current"));


        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng point) {

                if (marker[1] != null) {
                    marker[1].remove();


                }

                marker[1] = map.addMarker(new MarkerOptions()
                        .position(point)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

                final Dialog mDialog = new Dialog(Location.this);

                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                mDialog.setContentView(R.layout.dialoglayout);
                mDialog.show();
                WindowManager.LayoutParams wmlp = mDialog.getWindow().getAttributes();
                wmlp.gravity = Gravity.NO_GRAVITY;
                TextView no = (TextView) mDialog.findViewById(R.id.tv_dialog_no);
                TextView yes = (TextView) mDialog.findViewById(R.id.tv_dialog_yes);

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();

                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        String lt = String.valueOf(latLng.latitude);
                        String ln = String.valueOf(latLng.longitude);
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Location.this);

                        id = String.valueOf(sharedPref.getInt("id", -1));
                        postdataparams = new HashMap<String, String>();
                        postdataparams.put("title", title);
                        postdataparams.put("summary", summary);
                        postdataparams.put("description", description);
                        postdataparams.put("budget", budget);
                        postdataparams.put("e_time", time);
                        postdataparams.put("latitude", lt);
                        postdataparams.put("longitude", ln);
                        postdataparams.put("user_id", id);
                        PostAsyncTask task = new PostAsyncTask(Location.this, postdataparams);
                        task.execute();
                        Intent intent = new Intent(Location.this, Info_wall.class);
                        startActivity(intent);
                    }
                });


            }
        });


    }


    @Override
    public void onLocationChanged(android.location.Location location) {
        if (marker[0] != null) {
            marker[0].remove();
            lat = loc.getLatitude();
            lng = loc.getLongitude();
            marker[0] = googlemap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));

        } else {
            lat = loc.getLatitude();
            lng = loc.getLongitude();
            marker[0] = googlemap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onPlaceSelected(Place place) {

        // Format the returned place's details and display them in the TextView.


    }


    @Override
    public void onError(Status status) {

    }

    public void onSuccessfulSignUp(String responseString) {
        Intent intent = new Intent(Location.this, Info_wall.class);
        startActivity(intent);
    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack = Snackbar.make(rl_loc, responseString, Snackbar.LENGTH_LONG);
        snack.show();
    }
}
