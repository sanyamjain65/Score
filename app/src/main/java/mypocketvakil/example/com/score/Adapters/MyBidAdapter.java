package mypocketvakil.example.com.score.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
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

public class MyBidAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    TextView title,min,max,status,budget;
    ImageView iv_mybids;
    Context context;
    public MyBidAdapter(Context context, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.mybids_list, null);

         title = (TextView)vi.findViewById(R.id.tv_mybids_title);
         min = (TextView)vi.findViewById(R.id.tv_mybidbid_min);
         max = (TextView)vi.findViewById(R.id.tv_mybid_max);
         status = (TextView)vi.findViewById(R.id.tv_mybid_status);
         budget = (TextView)vi.findViewById(R.id.tv_mybid_budget);


         iv_mybids=(ImageView)vi.findViewById(R.id.iv_mybids);


        HashMap<String, String> song = new HashMap <String, String>();
        song = data.get(i);
        String text=song.get("title");
        String budget_min=song.get("budget_min");
        String budget_max=song.get("budget_max");
        String stat=song.get("status");
        String bid=song.get("bid");


        String img=song.get("image");
        byte[] decode= Base64.decode(img,Base64.DEFAULT);
        Bitmap decodebitmap= BitmapFactory.decodeByteArray(decode,0,decode.length);

        Bitmap circleBitmap = Bitmap.createBitmap(decodebitmap.getWidth(), decodebitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Rect rect = new Rect(0, 0, decodebitmap.getWidth(), decodebitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 1000;
        BitmapShader shader = new BitmapShader(decodebitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawRoundRect(rectF, roundPx, roundPx, paint);

        // Setting all values in listview
        title.setText(text);
        min.setText(budget_min);
        max.setText(budget_max);
        status.setText(stat);
        budget.setText(bid);
        iv_mybids.setImageBitmap(circleBitmap);


        return vi;
    }
}

