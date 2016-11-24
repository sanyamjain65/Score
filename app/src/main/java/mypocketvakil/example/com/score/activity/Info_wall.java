package mypocketvakil.example.com.score.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.JavaClasses.CircleTransform;
import mypocketvakil.example.com.score.NetworkCall.HttpHandler;
import mypocketvakil.example.com.score.Preferences.SharedPreference;
import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.fragment.Fragment_post;
import mypocketvakil.example.com.score.fragment.get;
import mypocketvakil.example.com.score.fragment.post;
import mypocketvakil.example.com.score.fragment.post2;

public class Info_wall extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = Info_wall.class.getSimpleName();
    private static final String url = "http://172.16.101.109:138/post/";
    ListView listView;
    TextView name,tv_nav_name,tv_nav_email;
    ProgressDialog progressDialog;
    ImageView iv_nav;
    String jsonStr;
    ArrayList<HashMap<String, String>> contactlist;
    private TabLayout tabLayout;
    String name1,mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_wall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        contactlist = new ArrayList<>();
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
        listView = (ListView) findViewById(R.id.list);
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
        name = (TextView) findViewById(R.id.tv_toolbar_name);

        int id = item.getItemId();

        if (id == R.id.home) {

            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
                getSupportFragmentManager().popBackStack();
            }

            listView.setVisibility(View.VISIBLE);
            name.setText("FEED");

        } else if (id == R.id.post) {

            listView.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, frag).addToBackStack(null).commit();

            name.setText("POST");


        } else if (id == R.id.profile) {
            Intent i = new Intent(Info_wall.this, user.class);
            startActivity(i);
            this.finish();

        } else if (id == R.id.settings) {

        } else if (id == R.id.nav_share) {

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

    public void onSignUpFailed(String responseString) {
        Intent i=new Intent (Info_wall.this,user.class);
        startActivity(i);
    }

    public void onSuccessfulSignUp(String responseString) {
        Intent intent=new Intent(Info_wall.this,Login.class);
        startActivity(intent);
    }


    private class getContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String title = c.getString("title");
                        String summary = c.getString("summary");
                        String budget = c.getString("budget");


                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("title", title);
                        contact.put("summary", summary);
                        contact.put("budget", budget);

                        contactlist.add(contact);


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
            progressDialog = new ProgressDialog(Info_wall.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            ListAdapter listAdapter = new SimpleAdapter(Info_wall.this, contactlist, R.layout.list_row, new String[]{"title", "summary", "budget"}, new int[]{R.id.tv_listview_title, R.id.tv_listview_summary, R.id.tv_listview_budget});
            listView.setAdapter(listAdapter);

        }

    }
}
