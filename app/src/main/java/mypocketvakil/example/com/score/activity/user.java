package mypocketvakil.example.com.score.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import mypocketvakil.example.com.score.Preferences.SharedPreference;
import mypocketvakil.example.com.score.R;


public class user extends AppCompatActivity {
    final int a = 1;
    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;
    String currentphotopath;
    private ImageView mainimage;
    private EditText imagerelative;
    private RelativeLayout mainrelative;
    TextView tv_user_name;
    //captured picture uri
    private Uri picUri;
    String name1;
  /*  public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int w = realImage.getWidth();
        int h = realImage.getHeight();
        w = h;
        int width = Math.round((float) ratio * w);
        int height = Math.round((float) ratio * h);

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
*/
    /**
     *
     * @param finalBitmap
     * @return path of file where bimap is used
     */
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
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            SharedPreference sharedPreference=SharedPreference.getInstance(this);
            sharedPreference.saveValue("image_path",file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(user.this,Info_wall.class);
                startActivity(i);

                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
            }
        }, 100);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                picUri = data.getData();
                performCrop();
            } else if (requestCode == PIC_CROP) {
                //get the returned data
                Bundle extras = data.getExtras();
//get the cropped bitmap
//                Bitmap thePic = extras.getParcelable("data");
                ImageView picView = (ImageView) findViewById(R.id.mainimage);
//display the returned cropped image
//                picView.setImageBitmap(thePic);
                Bitmap bitmap = extras.getParcelable("data");

                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);
                final float roundPx = 10;

                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                paint.setAntiAlias(true);
                Canvas c = new Canvas(circleBitmap);
                c.drawRoundRect(rectF, roundPx, roundPx, paint);


                picView.setImageBitmap(circleBitmap);

            }

        }
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                picUri = data.getData();
                //carry out the crop operation
                performCrop();
            } else if (requestCode == PIC_CROP) {
                //get the returned data
                Bundle extras = data.getExtras();
//get the cropped bitmap
//                Bitmap thePic = extras.getParcelable("data");
                ImageView picView = (ImageView) findViewById(R.id.mainimage);
//display the returned cropped image
//                picView.setImageBitmap(thePic);
                Bitmap bitmap = extras.getParcelable("data");

                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                final RectF rectF = new RectF(rect);
                final float roundPx = 10;

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
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mainimage = (ImageView) findViewById(R.id.mainimage);
        mainrelative = (RelativeLayout) findViewById(R.id.mainrelative);
        mainrelative = (RelativeLayout) findViewById(R.id.imagerelative);
        tv_user_name=(TextView)findViewById(R.id.tv_user_name);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(user.this);
        name1 = sharedPref.getString("name", null);
        tv_user_name.setText(name1);
        mainimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(user.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                action();
                else ActivityCompat.requestPermissions(user.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        });
     String image=   SharedPreference.getInstance(this).getString("image_path");
        if(image!=null && image.length()>0){
            File file=new File(image);
            if(file.exists())
                Glide.with(this).load(file).into(mainimage);
            else Glide.with(this).load(image).into(mainimage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && permissions[0].equalsIgnoreCase(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            action();
        else Toast.makeText(this,"permission not granted",Toast.LENGTH_LONG).show();
    }

    private void action() {

        final Dialog dialog = new Dialog(user.this);
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
                    Toast toast = Toast.makeText(user.this, errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        dialog.show();

    }

}

