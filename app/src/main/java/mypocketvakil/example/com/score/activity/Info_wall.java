package mypocketvakil.example.com.score.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import mypocketvakil.example.com.score.AsyncTask.BidAsyncTask;
import mypocketvakil.example.com.score.JavaClasses.CircleTransform;
import mypocketvakil.example.com.score.JavaClasses.GeoLoc;
import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.NetworkCall.NetworkKeys;
import mypocketvakil.example.com.score.Preferences.SharedPreference;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.Services.SimpleWakefulService;
import mypocketvakil.example.com.score.fragment.Fragment_post;
import mypocketvakil.example.com.score.fragment.get;
import mypocketvakil.example.com.score.fragment.post;
import mypocketvakil.example.com.score.fragment.post2;

public class Info_wall extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {
    GeoLoc g=new GeoLoc().getInstance();
    private static final String TAG = Info_wall.class.getSimpleName();
    private static final String url = "http://172.16.101.109:138/post/";
    ListView listView;
    TextView name,tv_nav_name,tv_nav_email;
    ProgressDialog progressDialog;
    ImageView iv_nav,iv_wall;
    String jsonStr;
    ArrayList<HashMap<String, String>> contactlist;
    private TabLayout tabLayout;
    String name1,mail,id;
    GoogleMap map;
    int l;
    ProgressBar progressBar;
    double lat[]=new double[200];
    double longi[]=new double[200];
    String title[]=new String[200];
    String budget_min[]=new String[200];
    String budget_max[]=new String[200];
    String category[]=new String[200];
    String time[]=new String[200];
    String description[]=new String[200];
    String distance[]=new String[200];
    String payment[]=new String[200];
    String user_id[]=new String[200];
    String id1[]=new String[200];
    String source_add[]=new String[200];
    private static final CharSequence[] MAP_TYPE_ITEMS =
            {"Road Map", "Satellite", "Terrain", "Hybrid"};

    String user_image[]=new String[20];
    String work_image1[]=new String[200];
    HashMap<String,String> postdataparams;
    String bid;
    RelativeLayout content_frame;
    ImageView iv_swap1;

    int c=0;
    int a,b;
    Marker marker[]=new Marker[500];
    Marker current;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_wall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        Calendar cur_cal = Calendar.getInstance();
        cur_cal.setTimeInMillis(System.currentTimeMillis());
        Log.d("Testing", "Calender Set time:" + cur_cal.getTime());
        Intent intent = new Intent(Info_wall.this, SimpleWakefulService.class);
        Log.d("Testing", "Intent created");
        PendingIntent pi = PendingIntent.getService(Info_wall.this, 0, intent, 0);
        AlarmManager alarm_manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm_manager.setRepeating(AlarmManager.RTC, cur_cal.getTimeInMillis(), 60000, pi);
        Log.d("Testing", "alarm manager set");

        iv_wall=(ImageView)findViewById(R.id.iv_wall);
        iv_swap1=(ImageView)findViewById(R.id.iv_swap1);
        iv_swap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMapTypeSelectorDialog();
            }
        });


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map1);

        mapFragment.getMapAsync(Info_wall.this);
        contactlist = new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.progressBar1);
        content_frame=(RelativeLayout)findViewById(R.id.content_frame);
//        progressBar.setVisibility(View.GONE);
        latitude=g.getLatitude();
        longitude=g.getLongitude();
        View mapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getView();
        View btnMyLocation = ((View) mapView.findViewById(1).getParent()).findViewById(2);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(85, 85); // size of button in dp
//        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.setMargins(620, 980, 10, 0);
        btnMyLocation.setLayoutParams(params);
//        Fragment_post f1=new Fragment_post();
//
//        Intent intent=getIntent();
//        String frag = intent.getExtras().getString("frag");
//        if(frag.equals("fragment"))
//        {
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f1).addToBackStack(null).commit();
//        }


//        get newfrag=new get();
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,newfrag).commit();
//        listView = (ListView) findViewById(R.id.list);
//        new getContacts().execute();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View nav_name =  navigationView.getHeaderView(0);



        tv_nav_email=(TextView)nav_name.findViewById(R.id.tv_nav_email);
        tv_nav_name=(TextView)nav_name.findViewById(R.id.tv_nav_name);
        iv_nav=(ImageView)nav_name.findViewById(R.id.iv_nav);

        //navigationView.getMenu().getItem(3).setActionView(R.layout.wallet);

//        TextView text=(TextView)e.findViewById(R.id.wallet_text);
//        text.setText(60);
        String image=   SharedPreference.getInstance(this).getString("image_path");
        if(image!=null && image.length()>0){
            File file=new File(image);
            if(file.exists()){
                Glide.with(this).load(file)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_nav);
            }

            else
            {
                Glide.with(this).load(image)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_nav);
            }
        }
        String image1=   SharedPreference.getInstance(this).getString("image_path");
        if(image1!=null && image1.length()>0){
            File file=new File(image1);
            if(file.exists()){
                Glide.with(this).load(file)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_wall);
            }

            else
            {
                Glide.with(this).load(image1)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_wall);
            }
        }
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Info_wall.this);
        name1 = sharedPref.getString("name", null);
        mail = sharedPref.getString("mail", null);

        tv_nav_name.setText(name1);
        tv_nav_email.setText(mail);
        navigationView.setNavigationItemSelectedListener(this);
//        tabLayout = (TabLayout) findViewById(R.id.tab_layout);


        //Adding the tabs using addTab() method
//        tabLayout.addTab(tabLayout.newTab().setText("Get"));
//        tabLayout.addTab(tabLayout.newTab().setText("Post"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
        //Initializing viewPager
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//        Creating our pager adapter
//        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),  Info_wall.this);
//
//        Adding adapter to pager
//        viewPager.setAdapter(adapter);
//
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(PagerAdapter.getTabView(i));
//        }
//       tabLayout.setupWithViewPager(viewPager);

        //Adding onTabSelectedListener to swipe views
//        tabLayout.setOnTabSelectedListener(this);
//        location=(TextView)findViewById(R.id.tv_post_location);
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(Info_wall.this,Location.class);
//                startActivity(i);
//            }
//        });


    }

    private void showMapTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = "Select Map Type";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fDialogTitle);

        // Find the current map type to pre-check the item representing the current state.
        int checkItem = map.getMapType() - 1;

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
                                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case 3:
                                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            default:
                                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            getSupportFragmentManager().popBackStack();
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        get fragment = new get();
        post newFragment = new post();
        Fragment_post frag=new Fragment_post();
        final post2 newFrag = new post2();
//        name = (TextView) findViewById(R.id.tv_toolbar_name);
//
        int id = item.getItemId();

        if (id == R.id.home) {

//            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
//                getSupportFragmentManager().popBackStack();
//            }
//
//            listView.setVisibility(View.VISIBLE);
//            name.setText("FEED");

        } else if (id == R.id.post) {

//            listView.setVisibility(View.INVISIBLE);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag).addToBackStack(null).commit();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(Info_wall.this,Post.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }
            },200);


//            name.setText("POST");


        } else if (id == R.id.profile) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Info_wall.this, user.class);
                    startActivity(i);

                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();
                }
            },200);


        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.logout) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Info_wall.this);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.remove("access_token");

            editor.commit();
            Intent intent = new Intent(Info_wall.this, Login.class);
            startActivity(intent);
            this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map=googleMap;
        final LatLng latLng = new LatLng(latitude, longitude);
//        current= googleMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//        current.setTag(250);
        new getContacts().execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        },2000);


        if(ActivityCompat.checkSelfPermission(Info_wall.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            map.setMyLocationEnabled(true);
        else ActivityCompat.requestPermissions(Info_wall.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                a = (int) marker.getTag();
                if (a == 250) {
                    googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            View w = getLayoutInflater().inflate(R.layout.info_current, null);

                            return w;


                        }

                        @Override
                        public View getInfoContents(Marker marker) {
                            View w = getLayoutInflater().inflate(R.layout.info_current, null);

                            return w;
                        }
                    });

                } else {
                    googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            View v = getLayoutInflater().inflate(R.layout.info_window, null);

                            TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
                            TextView tv_location = (TextView) v.findViewById(R.id.tv_location);
                            TextView tv_min = (TextView) v.findViewById(R.id.tv_min);
                            TextView tv_max = (TextView) v.findViewById(R.id.tv_max);
                            ImageView iv_info=(ImageView)v.findViewById(R.id.iv_info_window);
                            byte[] decode= Base64.decode(user_image[a],Base64.DEFAULT);
                            Bitmap decodebitmap= BitmapFactory.decodeByteArray(decode,0,decode.length);
                            iv_info.setImageBitmap(decodebitmap);
                            tv_title.setText(title[a]);
                            tv_location.setText(distance[a]);
                            tv_min.setText(budget_min[a]);
                            tv_max.setText(budget_max[a]);

                            return v;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {
                            View v = getLayoutInflater().inflate(R.layout.info_window, null);

                            return v;
                        }
                    });
                }
                    return false;
                }

        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                b= (int) marker.getTag();
                if(b==250)
                {

                }

                else
                {
                    final Dialog dialog = new Dialog(Info_wall.this);
                    // Include dialog.xml file
                    dialog.setContentView(R.layout.info_more);
                    TextView tv_info_title=(TextView)dialog.findViewById(R.id.tv_info_title);
                    TextView tv_info_category1=(TextView)dialog.findViewById(R.id.tv_info_category1);
                    TextView tv_info_payment_mode=(TextView)dialog.findViewById(R.id.tv_info_payment_mode);
                    TextView tv_info_time=(TextView)dialog.findViewById(R.id.tv_info_time);
                    TextView tv_info_description=(TextView)dialog.findViewById(R.id.tv_info_description);
                    TextView tv_info_location=(TextView)dialog.findViewById(R.id.tv_info_location);
                    TextView tv_info_min=(TextView)dialog.findViewById(R.id.tv_info_min);
                    TextView tv_info_max=(TextView)dialog.findViewById(R.id.tv_info_max);
                    TextView tv_info_submit=(TextView)dialog.findViewById(R.id.tv_info_submit);
                    TextView tv_info_address=(TextView)dialog.findViewById(R.id.tv_info_address);
                    ImageView iv_info_user=(ImageView)dialog.findViewById(R.id.iv_info_user);
                    ImageView iv_info_work=(ImageView)dialog.findViewById(R.id.iv_info_work);
                    byte[] decode= Base64.decode(user_image[b],Base64.DEFAULT);
                    Bitmap decodebitmap= BitmapFactory.decodeByteArray(decode,0,decode.length);
                    iv_info_user.setImageBitmap(decodebitmap);
                    byte[] decode1= Base64.decode(work_image1[b],Base64.DEFAULT);
                    Bitmap decodebitmap1= BitmapFactory.decodeByteArray(decode1,0,decode1.length);
                    tv_info_address.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Info_wall.this);
                            builder.setCancelable(true);
                            builder.setMessage(source_add[b]);

                            AlertDialog fMapTypeDialog = builder.create();
                            fMapTypeDialog.setCanceledOnTouchOutside(true);
                            fMapTypeDialog.show();

                        }
                    });



                    Bitmap circleBitmap = Bitmap.createBitmap(decodebitmap1.getWidth(), decodebitmap1.getHeight(), Bitmap.Config.ARGB_8888);
                    final Rect rect = new Rect(0, 0, decodebitmap1.getWidth(), decodebitmap1.getHeight());
                    final RectF rectF = new RectF(rect);
                    final float roundPx = 1000;

                    BitmapShader shader = new BitmapShader(decodebitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setShader(shader);
                    paint.setAntiAlias(true);
                    Canvas c = new Canvas(circleBitmap);
                    c.drawRoundRect(rectF, roundPx, roundPx, paint);
                    iv_info_work.setImageBitmap(circleBitmap);

                    final EditText et_info_bid=(EditText)dialog.findViewById(R.id.et_info_bid);
                    tv_info_title.setText(title[b]);
                    tv_info_category1.setText(category[b]);
                    tv_info_payment_mode.setText(payment[b]);
                    tv_info_time.setText(time[b]);
                    tv_info_description.setText(description[b]);
                    tv_info_location.setText(distance[b]);
                    tv_info_min.setText(budget_min[b]);
                    tv_info_max.setText(budget_max[b]);

                    dialog.show();


                    tv_info_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Info_wall.this);
                            id = String.valueOf(sharedPref.getInt("id", -1));
                            String identity=id1[b];
                            postdataparams=new HashMap<String, String>();
                            postdataparams.put("post_id",identity);         //id of user
                            postdataparams.put("bid",et_info_bid.getText().toString());
                            postdataparams.put("user_id",id);                //id of user who bids
                            BidAsyncTask task=new BidAsyncTask(Info_wall.this,postdataparams,identity);
                            task.execute();
                            dialog.dismiss();
                        }
                    });






                }

            }
        });





    }




    private class getContacts extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(NetworkKeys.NET_KEY.POST_URL);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);
                    l=contacts.length();
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        lat[i] = Double.parseDouble(c.getString("dest_lat"));
                        longi[i]= Double.parseDouble(c.getString("dest_long"));


                        id1[i]=c.getString("id");
                        title[i]=c.getString("work");
                        budget_min[i]=c.getString("budget_min");
                        budget_max[i]=c.getString("budget_max");
                        distance[i]=c.getString("near_by");
                        payment[i]=c.getString("payment");
                        category[i]=c.getString("category");
                        description[i]=c.getString("details");
                        time[i]=c.getString("time");
                        user_id[i]=c.getString("user_id");
                        source_add[i]=c.getString("source_address");
                        String user_img=c.getString("usr_image");
                        String work_img=c.getString("image");

                        if(user_img.equals("no image")){
                            user_image[i]="no image";

                        }
                        else{
                            user_image[i]=user_img;

//                            imageView.setImageBitmap(decodebitmap);
                        }
                        if(work_img.equals("no image")){
                            work_image1[i]="no image";

                        }
                        else{
                            work_image1[i]=work_img;

//                            imageView.setImageBitmap(decodebitmap);
                        }









                    }

                } catch (Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressBar.isShown()) {
                progressBar.setVisibility(View.GONE);
            }

            for ( int i=0;i<l;i++)
            {
//            drawMarker(new LatLng(lat[i],longi[i]));
                LatLng ll=new LatLng(lat[i],longi[i]);
                marker[i]=map.addMarker(new MarkerOptions()
                        .position(ll)

                );
                marker[i].setTag(i);
//                map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                    @Override
//                    public View getInfoWindow(Marker marker) {
//
//
//                    }
//
//                    @Override
//                    public View getInfoContents(Marker marker) {
//
//
//                    }
//                });
            }
//            ListAdapter listAdapter = new SimpleAdapter(Info_wall.this, contactlist, R.layout.list_row, new String[]{"title", "summary", "budget"}, new int[]{R.id.tv_listview_title, R.id.tv_listview_summary, R.id.tv_listview_budget});
//            listView.setAdapter(listAdapter);


        }

    }
    public void onSuccessfulSignUp(String responseString) {

        Snackbar snack = Snackbar.make(content_frame, responseString, Snackbar.LENGTH_LONG);
        snack.show();

    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack = Snackbar.make(content_frame, responseString, Snackbar.LENGTH_LONG);
        snack.show();
    }
}
