package com.stc.launchersetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.stc.launchersetting.R;


public class MainActivity extends PreferenceActivity
{

    private static String DEBUG_TAG="SETTING:";
    private SharedPreferences settings;
    private SelfPreference mSelfPreference;
    private PreferenceScreen mPreferenceScreen;
    private CheckBoxPreference mSwitcher;
    public static String ACTION_LAUNCHER_SETTING_LOCK_DESK = "com.stc.launchersetting.lock";
    public static String ACTION_LAUNCHER_SETTING_PICK_COLOR = "com.stc.launchersetting.pickcolor";
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //initPreferenceScreen();
        initPreferenceFromSource();
        mSwitcher = (CheckBoxPreference)findPreference("pref_switcher");
        mSwitcher.setOnPreferenceChangeListener(mLockerListener);

    }

    private Preference.OnPreferenceChangeListener mLockerListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String message = "锁定桌面后\n无法对桌面进行操作！";
            if (o.toString().equals("true")) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                preference.setSummary("开启");
            } else {
                preference.setSummary("未开启");
            }

            Intent intent = new Intent();
            intent.setAction(ACTION_LAUNCHER_SETTING_LOCK_DESK);

            intent.putExtra("pre_switcher",o.toString());
            /*Bundle bundle = new Bundle();
            bundle.putString("pref_switcher", o.toString());
            //intent.getBundleExtra("data",bundle);
            intent.putExtra("data", bundle);
           // intent.putExtras(bundle);*/
            sendBroadcast(intent);
            return true;
        }
    };
    private void initPreferenceFromSource()
    {
        addPreferencesFromResource(R.xml.preferencescreen);
    }
    private void initSelfPreference()
    {
        mSelfPreference = new SelfPreference(this);
        mSelfPreference.setOrder(1);
        mSelfPreference.setTitle("通用设置");
        mSelfPreference.setKey("SelfPref");
        mPreferenceScreen.addPreference(mSelfPreference);

        SelfCheckBoxPreference mSelfPreference1;
        mSelfPreference1 = new SelfCheckBoxPreference(this);
        mSelfPreference1.setOrder(2);
        mSelfPreference1.setTitle("系统设置");
        mSelfPreference1.setKey("system");

        mPreferenceScreen.addPreference(mSelfPreference1);
    }
    private void initPreferenceScreen()
    {
        addPreferencesFromResource(R.xml.preference);
        mPreferenceScreen = getPreferenceScreen();
        mPreferenceScreen.setOrderingAsAdded(true);
        this.getListView().setBackgroundResource(R.drawable.abc_ab_share_pack_holo_dark);

        initSelfPreference();
    }




    @Override
    protected void onResume() {
        super.onResume();
        if(mSwitcher.isChecked())
        {
            mSwitcher.setSummary("已开启");
        }
        else
        {
            mSwitcher.setSummary("未开启");
        }

    }
}
