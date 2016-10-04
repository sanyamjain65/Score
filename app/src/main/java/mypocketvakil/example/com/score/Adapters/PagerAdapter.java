package mypocketvakil.example.com.score.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import mypocketvakil.example.com.score.R;
import mypocketvakil.example.com.score.activity.Info_wall;
import mypocketvakil.example.com.score.fragment.get;
import mypocketvakil.example.com.score.fragment.post;

/**
 * Created by sanyam jain on 28-09-2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    static Context context;

    static String tabTitles[] = new String[]{"Get", "Post"};




    public PagerAdapter(FragmentManager fm,  Context context) {
        super(fm);
        //Initializing tab count

        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                get get = new get();
                return get;
            case 1:
                post post = new post();
                return post;
            default:
                return null;
        }


    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public static View getTabView(int position) {
        View tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(tabTitles[position]);
        return tab;


    }

}