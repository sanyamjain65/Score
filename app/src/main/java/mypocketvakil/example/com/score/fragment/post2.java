package mypocketvakil.example.com.score.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.akashandroid90.googlesupport.location.AppLocationFragment;
import com.github.akashandroid90.googlesupport.location.AppLocationSupportFragment;
import com.google.android.gms.common.ConnectionResult;

import java.util.Calendar;

import mypocketvakil.example.com.score.JavaClasses.GeoLoc;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.activity.Location;


public class post2 extends AppLocationSupportFragment {
    EditText et_post2_budget;
    RadioButton rb_days, rb_hours;
    EditText et_post2_days;
    EditText et_post2_hours;
    ImageView iv_post2_days;
    ImageView iv_post2_hours;
    double latitude;
    double longitude;
    TextView location;
    String budget;

    String time;
    String title, summary, description;
    RelativeLayout rel_post2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post2, container, false);


    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_post2_budget = (EditText) view.findViewById(R.id.et_post2_budget);
        et_post2_days = (EditText) view.findViewById(R.id.et_post2_days);
        et_post2_hours = (EditText) view.findViewById(R.id.et_post2_hours);
        iv_post2_days = (ImageView) view.findViewById(R.id.iv_post2_days);
        iv_post2_hours = (ImageView) view.findViewById(R.id.iv_post2_hours);
        rel_post2 = (RelativeLayout) view.findViewById(R.id.rel_post2);
        rb_days = (RadioButton) view.findViewById(R.id.rb_days);
        rb_hours = (RadioButton) view.findViewById(R.id.rb_hours);
        location = (TextView) view.findViewById(R.id.tv_post2_button);

        title = getArguments().getString("title");
        summary = getArguments().getString("summary");
        description = getArguments().getString("description");


        iv_post2_hours.setVisibility(View.GONE);
        iv_post2_days.setVisibility(View.GONE);
        et_post2_hours.setVisibility(View.GONE);
        et_post2_days.setVisibility(View.GONE);
        rb_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_days.isChecked()) {
                    rb_days.setChecked(false);
                    iv_post2_days.setVisibility(View.GONE);
                    et_post2_days.setVisibility(View.GONE);

                }


                rb_hours.isSelected();
                iv_post2_hours.setVisibility(View.VISIBLE);
                et_post2_hours.setVisibility(View.VISIBLE);


            }
        });
        rb_days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_hours.isChecked()) {
                    rb_hours.setChecked(false);
                    iv_post2_hours.setVisibility(View.GONE);
                    et_post2_hours.setVisibility(View.GONE);
                }

                rb_days.isSelected();
                iv_post2_days.setVisibility(View.VISIBLE);
                et_post2_days.setVisibility(View.VISIBLE);


            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget = et_post2_budget.getText().toString();
                String duration_hours = et_post2_hours.getText().toString();
                String duration_days = et_post2_days.getText().toString();
                String a = "";
                if ((duration_hours.equals(a)) && (duration_days.equals(a))) {
                    Snackbar snackbar = Snackbar.make(rel_post2, "Please select one out of days and hours", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    et_post2_days.setText("");
                    et_post2_hours.setText("");

                } else if ((!duration_days.equals(a)) && (duration_hours.equals(a))) {
                    Calendar d = Calendar.getInstance();
                    int years = d.get(Calendar.YEAR);
                    int month = d.get(Calendar.MONTH);
                    int day = d.get(Calendar.DAY_OF_MONTH);
                    int hours = d.get(Calendar.HOUR_OF_DAY);
                    int min = d.get(Calendar.MINUTE);
                    int sec = d.get(Calendar.SECOND);
                    day += Integer.parseInt(duration_days);
                    time = years + "-" + month + "-" + day + "T" + hours + ":" + min + ":" + sec;

                    Intent intent = new Intent(view.getContext(), Location.class);
                    intent.putExtra("budget", budget);
                    intent.putExtra("duration", time);
                    intent.putExtra("title", title);
                    intent.putExtra("summary", summary);
                    intent.putExtra("description", description);
                    startActivity(intent);


                } else if ((!duration_hours.equals(a)) && (duration_days.equals(a))) {
                    Calendar d = Calendar.getInstance();
                    int years = d.get(Calendar.YEAR);
                    int month = d.get(Calendar.MONTH);
                    int day = d.get(Calendar.DAY_OF_MONTH);
                    int hours = d.get(Calendar.HOUR_OF_DAY);
                    int min = d.get(Calendar.MINUTE);
                    int sec = d.get(Calendar.SECOND);


                    hours += Integer.parseInt(duration_hours);
                    time = years + "-" + month + "-" + day + "T" + hours + ":" + min + ":" + sec;

                    Intent intent = new Intent(view.getContext(), Location.class);
                    intent.putExtra("budget", budget);
                    intent.putExtra("duration", time);
                    intent.putExtra("title", title);
                    intent.putExtra("summary", summary);
                    intent.putExtra("description", description);
                    startActivity(intent);
                } else {
                    Snackbar snackbar = Snackbar.make(rel_post2, "Please enter either days or duration", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            }
        });


    }


    @Override
    public void myCurrentLocation(android.location.Location currentLocation) {
        super.myCurrentLocation(currentLocation);
        latitude = currentLocation.getLatitude();
        longitude = currentLocation.getLongitude();
        loc(latitude, longitude);

    }

    public GeoLoc loc(double latitude, double longitude) {


        GeoLoc g = new GeoLoc().getInstance();
        g.setLatitude(latitude);
        g.setLongitude(longitude);
        return g;


    }

    @Override
    public void onActivityResultError(int resultCode, ServiceError serviceError, ConnectionResult connectionResult) {

    }
}