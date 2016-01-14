package com.oxilo.shopsity.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nm on 11/14/2015.
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        setFont();
    }
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTStd Lt.otf");
        setTypeface(font, Typeface.NORMAL);
    }
}
