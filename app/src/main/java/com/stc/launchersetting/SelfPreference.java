package com.stc.launchersetting;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by cninlaxu on 2015/3/24.
 */
public class SelfPreference extends Preference
{
    private Context mContext;
    private ImageView mImageView;
    private View.OnClickListener mClickListener;

    public SelfPreference(Context context)
    {
        super(context);
        mContext = context;
        setLayoutResource(R.layout.switch_item);
    }
    public SelfPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        setLayoutResource(R.layout.switch_item);
    }
    @Override
    protected void onClick() {
        Log.d("Debug", "selfpreference on clicked");
        //
    }
    /*@Override
    protected View onCreateView(ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.switch_item, parent,false);
        View frame = layout.findViewById(R.id.switch_item_layout);
        TextView tv = (TextView)layout.findViewById(R.id.switch_item_text);
        String tvText = tv.getText().toString();
        tv.setVisibility(View.VISIBLE);
        ImageButton img = (ImageButton)layout.findViewById(R.id.switch_item_imageview);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "ImageView on clicked");
            }
        });
        return frame;

    }

    protected void onBindView(View view)
    {
        super.onBindView(view);
        Log.d("Debug", "onBindView");
        TextView tv = (TextView)view.findViewById(R.id.switch_item_text);

        ImageButton img = (ImageButton)view.findViewById(R.id.switch_item_imageview);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "ImageView on clicked");
            }
        });
    }
    */

}