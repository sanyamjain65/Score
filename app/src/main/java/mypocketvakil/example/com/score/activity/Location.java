package mypocketvakil.example.com.score.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.akashandroid90.googlesupport.location.AppLocationActivity;
import com.google.android.gms.common.ConnectionResult;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mypocketvakil.example.com.score.JavaClasses.GeoLoc;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.fragment.post;

public class Location extends AppLocationActivity implements LocationListener, OnMapReadyCallback, PlaceSelectionListener {
    GeoLoc loc = new GeoLoc().getInstance();
    Geocoder geocoder;
    double lat, latitude;
    public static final String MESSAGE="fragment";
    double lng, longitude;
    post p = new post();
    GoogleMap googlemap;
    Marker marker[] = new Marker[2];
    TextView help,tv_confirm;
    ImageView iv_help,iv_swap;
    boolean message = false;
    String title, summary, description, budget, time;
    HashMap<String, String> postdataparams;
    RelativeLayout rl_loc;
    String id,add;
    private static final CharSequence[] MAP_TYPE_ITEMS =
            {"Road Map", "Satellite", "Terrain", "Hybrid"};

    PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        boolean lowPowerMoreImportantThanAccurancy = true;
        LocationRequest request = LocationRequest.create();
        request.setPriority(lowPowerMoreImportantThanAccurancy ?
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY :
                LocationRequest.PRIORITY_HIGH_ACCURACY);


        geocoder = new Geocoder(Location.this, Locale.getDefault());

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        summary = intent.getStringExtra("summary");
        description = intent.getStringExtra("description");
        budget = intent.getStringExtra("budget");
        time = intent.getStringExtra("duration");
        iv_swap=(ImageView)findViewById(R.id.iv_swap);
        rl_loc = (RelativeLayout) findViewById(R.id.rl_loc);
        tv_confirm=(TextView)findViewById(R.id.tv_confirm);
        iv_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapTypeSelectorDialog();
            }
        });
//        help = (TextView) findViewById(R.id.tv_map_help);
//        help.setVisibility(View.GONE);
//        iv_help = (ImageView) findViewById(R.id.iv_map_question);
//        iv_help.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (message) {
//                    message = false;
//                    help.setVisibility(View.GONE);
//                } else {
//                    message = true;
//                    help.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(Location.this);

        View mapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getView();
        View btnMyLocation = ((View) mapView.findViewById(1).getParent()).findViewById(2);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80); // size of button in dp
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.setMargins(650, 920, 10, 0);
        btnMyLocation.setLayoutParams(params);


         autocompleteFragment = (PlaceAutocompleteFragment)
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




        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng point) {

                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(point.latitude, point.longitude,1);
                } catch (IOException e) {

                    e.printStackTrace();
                            }
                if (addresses.size()==0)
                {
                    Toast.makeText(Location.this,"Check your network Connection",Toast.LENGTH_LONG).show();

                }
                else {
                    android.location.Address address = addresses.get(0);


                    if (address != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                            sb.append(address.getAddressLine(i) + "\n");
                        }
                        Toast.makeText(Location.this, sb.toString(), Toast.LENGTH_LONG).show();
                        add=sb.toString();
                    }

                }


                if (marker[1] != null) {
                    marker[1].remove();


                }

                marker[1] = map.addMarker(new MarkerOptions()
                        .position(point)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                tv_confirm.setText("Confirm");
                tv_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String lt=String.valueOf(point.latitude);
                        String ln=String.valueOf(point.longitude);
                        Intent intent=new Intent(Location.this, Info_wall.class);
                        intent.putExtra("lat",lt);
                        intent.putExtra("long",ln);
                        intent.putExtra("address",add);
                        setResult(Activity.RESULT_OK,intent);
                        finish();



                    }
                });

//                final Dialog mDialog = new Dialog(Location.this);
//
//                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//                mDialog.setContentView(R.layout.dialoglayout);
//                mDialog.show();
//                WindowManager.LayoutParams wmlp = mDialog.getWindow().getAttributes();
//                wmlp.gravity = Gravity.NO_GRAVITY;
//                TextView no = (TextView) mDialog.findViewById(R.id.tv_dialog_no);
//                TextView yes = (TextView) mDialog.findViewById(R.id.tv_dialog_yes);
//
//                no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDialog.dismiss();
//
//                    }
//                });
//                yes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDialog.dismiss();
//                        String lt = String.valueOf(latLng.latitude);
//                        String ln = String.valueOf(latLng.longitude);
//                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Location.this);
//
//                        id = String.valueOf(sharedPref.getInt("id", -1));
//                        postdataparams = new HashMap<String, String>();
//                        postdataparams.put("title", title);
//                        postdataparams.put("summary", summary);
//                        postdataparams.put("description", description);
//                        postdataparams.put("budget", budget);
//                        postdataparams.put("e_time", time);
//                        postdataparams.put("latitude", lt);
//                        postdataparams.put("longitude", ln);
//                        postdataparams.put("user_id", id);
//                        PostAsyncTask task = new PostAsyncTask(Location.this, postdataparams);
//                        task.execute();
//                        Intent intent = new Intent(Location.this, Info_wall.class);
//                        startActivity(intent);
//                    }
//                });


            }
        });


    }

    private void showMapTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = "Select Map Type";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fDialogTitle);

        // Find the current map type to pre-check the item representing the current state.
        int checkItem = googlemap.getMapType() - 1;

        // Add an OnClickListener to the dialog, so that the selection will be handled.
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEMS,
                checkItem,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        // Locally create a finalised object.

                        // Perform an action depending on which item was selected.
                        switch (item) {
                            case 1:
                                googlemap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                googlemap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case 3:
                                googlemap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            default:
                                googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                        dialog.dismiss();
                    }
                }
        );

        // Build the dialog and show it.
        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }
    @Override
    public void onLocationChanged(android.location.Location location) {
        if (marker[0] != null) {
            marker[0].remove();


            lat = loc.getLatitude();
            lng = loc.getLongitude();
            marker[0] = googlemap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));

        } else {
//            myCurrentLocation(location );
            lat = loc.getLatitude();
            lng = loc.getLongitude();
            marker[0] = googlemap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
        }


    }
    public GeoLoc loc(double latitude, double longitude) {


        GeoLoc g = new GeoLoc().getInstance();
        g.setLatitude(latitude);
        g.setLongitude(longitude);
        return g;


    }
    @Override
    public void myCurrentLocation(android.location.Location currentLocation) {
        super.myCurrentLocation(currentLocation);
        latitude = currentLocation.getLatitude();
        longitude = currentLocation.getLongitude();
        loc(latitude, longitude);

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

    @Override
    public void onActivityResultError(int resultCode, ServiceError serviceError, ConnectionResult connectionResult) {

    }
}
