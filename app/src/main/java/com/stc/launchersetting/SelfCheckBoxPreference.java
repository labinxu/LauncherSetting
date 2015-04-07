package com.stc.launchersetting;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.SwitchPreference;
import android.util.AttributeSet;

import com.stc.launchersetting.R;

/**
 * Created by cninlaxu on 2015/3/25.
 */

public class SelfCheckBoxPreference extends CheckBoxPreference
{
    public SelfCheckBoxPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setLayoutResource(R.layout.checkbox_preference);
    }
    public SelfCheckBoxPreference(Context context)
    {
        super(context);
        setLayoutResource(R.layout.checkbox_preference);
    }
}