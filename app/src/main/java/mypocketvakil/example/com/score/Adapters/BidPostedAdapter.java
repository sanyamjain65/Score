package mypocketvakil.example.com.score.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import mypocketvakil.example.com.score.R;

/**
 * Created by sanyam jain on 05-12-2016.
 */

public class BidPostedAdapter extends BaseAdapter {
    TextView title,budget;
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    Context context;
    public BidPostedAdapter(Context context, ArrayList<HashMap<String, String>> d) {
        this.context = context;
        data=d;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.work_bid, null);

         title = (TextView)vi.findViewById(R.id.tv_workbidlist_name);
         budget = (TextView)vi.findViewById(R.id.tv_workbidlist_budget);

        ImageView image=(ImageView)vi.findViewById(R.id.iv_list_workbid);


        HashMap<String, String> song = new HashMap <String, String>();
        song = data.get(i);
        String name=song.get("name");
        String img=song.get("image");
        String bid=song.get("bid");

        byte[] decode= Base64.decode(img,Base64.DEFAULT);
        Bitmap decodebitmap= BitmapFactory.decodeByteArray(decode,0,decode.length);

        // Setting all values in listview
        title.setText(name);
        budget.setText(bid);
        image.setImageBitmap(decodebitmap);


        return vi;
    }
}
