package com.stc.launchersetting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.jar.Attributes;

/**
 * Created by cninlaxu on 2015/3/30.
 */
public class ColorPickerPreference extends DialogPreference{

    private static final String TAG = "ColorPickerPreference";
    private int mValue;
    private OnColorChangedListener mListener;
    private ColorPickerView mColorPickerView;
    private int mInitialColor;
    public ColorPickerPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        //setLayoutResource(R.layout.color_picker_view);
    }
    private View.OnClickListener mColorBlockListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Drawable background = view.getBackground();
            ColorDrawable colorDrawable = (ColorDrawable)background;
            mColorPickerView.setColor(colorDrawable.getColor());
        }
    };

    @Override
    public void onBindDialogView(View view)
    {
        super.onBindView(view);
        OnColorChangedListener l = new OnColorChangedListener() {
            @Override
            public void colorChanged(int color) {
                //mListener.colorChanged(color);
                mValue = color;
            }
        };
        mColorPickerView = (ColorPickerView)view.findViewById(R.id.color_picker_view);
        mColorPickerView.setOnColorChangeListener(l);

        Button bt = (Button)view.findViewById(R.id.color_picker_fuchsia);
        bt.setOnClickListener(mColorBlockListener);
        bt = (Button)view.findViewById(R.id.color_picker_green);
        bt.setOnClickListener(mColorBlockListener);
        bt = (Button)view.findViewById(R.id.color_picker_white);
        bt.setOnClickListener(mColorBlockListener);
        bt = (Button)view.findViewById(R.id.color_picker_black);
        bt.setOnClickListener(mColorBlockListener);
        bt = (Button)view.findViewById(R.id.color_picker_blue);
        bt.setOnClickListener(mColorBlockListener);
        bt = (Button)view.findViewById(R.id.color_picker_indigo);
        bt.setOnClickListener(mColorBlockListener);
        bt = (Button)view.findViewById(R.id.color_picker_yellow);
        bt.setOnClickListener(mColorBlockListener);
        bt = (Button)view.findViewById(R.id.color_picker_red);
        bt.setOnClickListener(mColorBlockListener);



    }
    public void setmValue(int value)
    {
        mValue = value;
    }
    public int getmValue()
    {
        return mValue;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        if (positiveResult)
        {
            this.getOnPreferenceChangeListener().onPreferenceChange(this, mValue);
        }
    }

    public interface OnColorChangedListener
    {
        void colorChanged(int color);
    }

    ////////////////////////////////////////////////////

}
