package mypocketvakil.example.com.score.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import mypocketvakil.example.com.score.R;

/**
 * Created by sanyam jain on 15-09-2016.
 */
public class CustomButton extends Button {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomButton(Context context) {
        super(context);
        applyCustomFont(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {

        TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        String fontName = attributeArray.getString(R.styleable.CustomView_font);



        Typeface customFont = selectTypeface(context, fontName);
        setTypeface(customFont);

        attributeArray.recycle();
    }


    private Typeface selectTypeface(Context context, String fontName) {

        if (fontName.contentEquals(context.getString(R.string.OpenSans_Light))) {
            return  FontCache.getTypeface("OpenSans-Light.ttf", context);

        }
        else if(fontName.contentEquals(context.getString(R.string.OpenSans_Reguler))){
            return  FontCache.getTypeface("OpenSans-Regular.ttf", context);
        }else if(fontName.contentEquals(context.getString(R.string.OpenSans_Semibold)))
        {
            return  FontCache.getTypeface("OpenSans-Semibold.ttf", context);
        }

        else {
            // no matching font found
            return FontCache.getTypeface("OpenSans-Regular.ttf", context);
        }
    }

}

