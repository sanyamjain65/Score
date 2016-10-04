package mypocketvakil.example.com.score.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.akashandroid90.googlesupport.location.AppLocationFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.OnMapReadyCallback;

import mypocketvakil.example.com.score.R;


public class post extends AppLocationFragment  {
    TextView location;
    


    public post() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ost, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        location=(TextView)view.findViewById(R.id.location);
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void myCurrentLocation(Location currentLocation) {
        super.myCurrentLocation(currentLocation);
//        location.setText(currentLocation.getLatitude()+","+currentLocation.getLongitude());
    }

    @Override
    public void onActivityResultError(int resultCode, ServiceError serviceError, ConnectionResult connectionResult) {

    }


}
