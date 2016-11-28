package mypocketvakil.example.com.score.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.akashandroid90.googlesupport.location.AppLocationActivity;
import com.google.android.gms.common.ConnectionResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import mypocketvakil.example.com.score.AsyncTask.PostAsyncTask;
import mypocketvakil.example.com.score.JavaClasses.GeoLoc;
import mypocketvakil.example.com.score.R;

public class Post extends AppLocationActivity {
    EditText et_work,et_detail,et_min,et_max;
    String category,duration;
    TextView tv_submit,tv_current_location,et_destination,tv_change;
    ImageView iv_image;
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;

    Button bt_post_paynow,bt_post_ondelivery;
    String pay,source_lat,source_long,dest_lat,dest_long,source_address,dest_address,id;
    boolean button=false;
    private HashMap postdataparams;
    String encodedImage;
    double latitude;
    double longitude;


    private Uri picUri;
    private String filepath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner1=(Spinner) findViewById(R.id.spinner1);
        et_work=(EditText)findViewById(R.id.et_work);
        tv_current_location=(TextView) findViewById(R.id.tv_current_location);
        et_destination=(TextView)findViewById(R.id.et_destination);
        et_detail=(EditText)findViewById(R.id.et_detail);
        et_min=(EditText)findViewById(R.id.et_min);
        et_max=(EditText)findViewById(R.id.et_max);
        tv_submit=(TextView)findViewById(R.id.tv_submit);
        iv_image=(ImageView)findViewById(R.id.iv_image);
        tv_change=(TextView)findViewById(R.id.tv_change);
        bt_post_paynow=(Button)findViewById(R.id.bt_post_paynow);
        bt_post_ondelivery=(Button)findViewById(R.id.bt_post_ondelivery);
        bt_post_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button==false){
                    pay="pay now";
                    bt_post_paynow.setBackgroundColor(Color.DKGRAY);
                    bt_post_ondelivery.setBackgroundColor(Color.LTGRAY);

                }
                button=true;


            }
        });
        bt_post_ondelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button==true|| button==false)
                {
                    pay="on delivery";
                    bt_post_paynow.setBackgroundColor(Color.LTGRAY);
                    bt_post_ondelivery.setBackgroundColor(Color.DKGRAY);
                }
                button=false;
            }
        });
        et_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),Location.class);
                startActivityForResult(i,5);
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), Location.class);
                startActivityForResult(intent,4);
            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(Post.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                    picture();
                else ActivityCompat.requestPermissions(Post.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        });



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        ArrayList categories = new ArrayList();
        categories.add("Delivery");
        categories.add("others");

        final ArrayList time=new ArrayList();
        time.add("Anytime");
        time.add("within 1 hour");
        time.add("within 2 hour");
        time.add("within 3 hour");
        time.add("within 4 hour");
        time.add("within 1 day");


        ArrayAdapter timeadapter=new ArrayAdapter(Post.this,android.R.layout.simple_spinner_item, time);
        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter(Post.this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner1.setAdapter(timeadapter);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image;
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Post.this);

                id = String.valueOf(sharedPref.getInt("id", -1));
                if(filepath==null||filepath=="")
                {
                    image="no image";

                }
                else{
                    image=filepath;

                }

                postdataparams = new HashMap<String, String>();
                postdataparams.put("user_id", id);
                postdataparams.put("work",et_work.getText().toString());
                postdataparams.put("category",category);
                postdataparams.put("source_lat",source_lat);
                postdataparams.put("source_long",source_long);
                postdataparams.put("dest_lat",dest_lat);
                postdataparams.put("dest_long",dest_long);
                postdataparams.put("source_address",source_address);
                postdataparams.put("dest_address",dest_address);
                postdataparams.put("time",duration);
                postdataparams.put("payment",pay);
                postdataparams.put("budget_min",et_min.getText().toString());
                postdataparams.put("budget_max",et_max.getText().toString());
                postdataparams.put("details",et_detail.getText().toString());
                postdataparams.put("image",image);

                PostAsyncTask task = new PostAsyncTask(Post.this, postdataparams);
                task.execute();






            }
        });
    }
    private void picture() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.NO_GRAVITY;

        TextView camera = (TextView) dialog.findViewById(R.id.camera);
        TextView gallery = (TextView) dialog.findViewById(R.id.galley);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 3);
                dialog.dismiss();

            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //use standard intent to capture an image
                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //we will handle the returned data in onActivityResult
                    startActivityForResult(captureIntent, CAMERA_CAPTURE);
                    dialog.dismiss();
                } catch (ActivityNotFoundException anfe) {
                    //display an error message
                    String errorMessage = "Whoops - your device doesn't support capturing images!";
                    Toast toast = Toast.makeText(Post.this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK)
        {
            if (requestCode==5)
            {
                source_lat=(String) data.getExtras().getString("lat");
                source_long=(String) data.getExtras().getString("long");
                source_address=(String) data.getExtras().getString("address");
                et_destination.setText(source_address);


            }
        }
        if (resultCode== Activity.RESULT_OK)
        {
            if (requestCode==4)
            {
                dest_lat=(String) data.getExtras().getString("lat");
                dest_long=(String) data.getExtras().getString("long");
                dest_address=(String) data.getExtras().getString("address");
                tv_current_location.setText(dest_address);





            }
        }
        if (resultCode == -1) {
            if (requestCode == 3) {
                picUri = data.getData();
//                filepath=picUri.getPath();
                performCrop();
            } else if (requestCode == PIC_CROP) {
                //get the returned data
                Bundle extras = data.getExtras();
//get the cropped bitmap
//                Bitmap thePic = extras.getParcelable("data");
                ImageView picView = (ImageView) findViewById(R.id.iv_image);
//display the returned cropped image
//                picView.setImageBitmap(thePic);
                Bitmap bitmap = extras.getParcelable("data");

                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);
                final float roundPx = 1000;

                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                paint.setAntiAlias(true);
                Canvas c = new Canvas(circleBitmap);
                c.drawRoundRect(rectF, roundPx, roundPx, paint);
                picView.setImageBitmap(circleBitmap);

            }

        }
        if (resultCode == -1) {
            if (requestCode == CAMERA_CAPTURE) {
                picUri = data.getData();
//                filepath=picUri.getPath();
                //carry out the crop operation
                performCrop();
            } else if (requestCode == PIC_CROP) {
                //get the returned data
                Bundle extras = data.getExtras();
//get the cropped bitmap
//                Bitmap thePic = extras.getParcelable("data");
                ImageView picView = (ImageView) findViewById(R.id.iv_image);
//display the returned cropped image
//                picView.setImageBitmap(thePic);
                Bitmap bitmap = extras.getParcelable("data");

                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);
                final float roundPx = 1000;

                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                paint.setAntiAlias(true);
                Canvas c = new Canvas(circleBitmap);
                c.drawRoundRect(rectF, roundPx, roundPx, paint);
                picView.setImageBitmap(circleBitmap);
                saveImage(circleBitmap);

            }
        }
    }

    private void saveImage(Bitmap finalBitmap) {
        if(finalBitmap==null)
            return;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            filepath=getStringImage(finalBitmap);
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
//            SharedPreference sharedPreference=SharedPreference.getInstance(this.getActivity());
//            sharedPreference.saveValue("image_path",file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void performCrop() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);

        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(Post.this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
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

    public void onSuccessfulSignUp(String responseString) {
        Intent i=new Intent (Post.this,user.class);
        startActivity(i);
    }

    public void onSignUpFailed(String responseString) {
        Intent i=new Intent (Post.this,Login.class);
        startActivity(i);
    }
}
