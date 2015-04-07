package com.stc.launchersetting;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;


import com.stc.launchersetting.R;
import com.stc.launchersetting.com.stc.launchersetting.provider.Setting;
import com.stc.launchersetting.com.stc.launchersetting.provider.SettingService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class UniversalSettingActivity extends PreferenceActivity {
    /**
     * Determines whether to always show the simplified settings UI, where
     * settings are presented in a single list. When false, settings are shown
     * as a master/detail two-pane view on tablets. When true, a single pane is
     * shown on tablets.
     */

    private static final boolean ALWAYS_SIMPLE_PREFS = false;

    private static final String DEBUG_TAG = "Settings";

    /*@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupSimplePreferencesScreen();
        Log.d(DEBUG_TAG, "onPostCreate");
    }*/
    private HashMap<String,Integer> _hmKeyToType;
    public SettingService settingService;
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        settingService = new SettingService(this);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new UniversalSettingPreferenceFragment(this)).commit();

        _hmKeyToType = new HashMap<String, Integer>();
        _hmKeyToType.put("pre_voice_broadcast", 1);
        _hmKeyToType.put("pre_dial_sound", 2);
        _hmKeyToType.put("pre_cover_pic", 3);
        _hmKeyToType.put("pre_lock_screen", 4);
        _hmKeyToType.put("pre_picked_color", 5);
        _hmKeyToType.put("pre_caller_identification",6);
    }

    public Set<String> getSettingKeys(){
        return _hmKeyToType.keySet();
    }

    public int getType(String key){
        return _hmKeyToType.get(key);
    }

    /**
     * Shows the simplified settings UI if the device configuration if the
     * device configuration dictates that a simplified, single-pane UI should be
     * shown.
     */
    private void setupSimplePreferencesScreen() {
        if (!isSimplePreferences(this)) {
            return;
        }

        addPreferencesFromResource(R.xml.pref_universal_setting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this) && !isSimplePreferences(this);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Determines whether the simplified settings UI should be shown. This is
     * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
     * doesn't have newer APIs like {@link PreferenceFragment}, or the device
     * doesn't have an extra-large screen. In these cases, a single-pane
     * "simplified" settings UI should be shown.
     */
    private static boolean isSimplePreferences(Context context) {
        return ALWAYS_SIMPLE_PREFS
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                || !isXLargeTablet(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        if (!isSimplePreferences(this)) {
            loadHeadersFromResource(R.xml.pref_headers, target);
        }
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String key = preference.getKey();
            String stringValue = value.toString();
            if (key.equals("pre_picked_color")) {
                try {
                    Log.d("Color picker:", "" + stringValue);
                    if (stringValue.equals("false") || stringValue.equals("true"))
                    {
                        return false;
                    }
                    Intent intent = new Intent();
                    intent.setAction(MainActivity.ACTION_LAUNCHER_SETTING_PICK_COLOR);
                    intent.putExtra("pre_picked_color", stringValue);
                    sendBroadcast(intent);

                    Setting setting = new Setting(key, getType(key));
                    setting.set_keyValue(Integer.valueOf(stringValue));
                    settingService.save(setting);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } else {
                int keyValue = 0;
                if (stringValue.equals("true")) {
                    preference.setSummary("已开启");
                    keyValue = 1;
                } else {
                    preference.setSummary("未开启");
                    keyValue = 0;
                }

                Intent intent = new Intent();
                intent.setAction(key);
                intent.putExtra(key, stringValue);
                sendBroadcast(intent);
                Setting setting = new Setting(preference.getKey(), getType(preference.getKey()));

                setting.set_keyValue(keyValue);
                settingService.save(setting);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getBoolean(preference.getKey(), false));
    }
    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class UniversalSettingPreferenceFragment extends PreferenceFragment {
        private Context context;
        public UniversalSettingPreferenceFragment(Context context)
        {
            this.context = context;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_universal_setting);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            UniversalSettingActivity activity = (UniversalSettingActivity)context;
            Set<String> settingKes = activity.getSettingKeys();
            Iterator it = settingKes.iterator();
            while (it.hasNext())
            {
                String key = (String) it.next();
                activity.bindPreferenceSummaryToValue(findPreference(key));
                Setting setting = new Setting(key, activity.getType(key));
                activity.settingService.save(setting);
            }

            /*activity.bindPreferenceSummaryToValue(findPreference("pre_voice_broadcast"));
            activity.bindPreferenceSummaryToValue(findPreference("pre_dial_sound"));
            activity.bindPreferenceSummaryToValue(findPreference("pre_caller_identification"));
            activity.bindPreferenceSummaryToValue(findPreference("pre_cover_pic"));
            activity.bindPreferenceSummaryToValue(findPreference("pre_lock_screen"));
            activity.bindPreferenceSummaryToValue(findPreference("pre_picked_color"));



            Setting voice_broadcast = new Setting("pre_voice_broadcast", 1);
            Setting dial_sound = new Setting("pre_dial_sound", 2);
            Setting cover_pic = new Setting("pre_cover_pic", 3);
            Setting lock_screen = new Setting("pre_lock_screen", 4);
            Setting picked_color = new Setting("pre_picked_color", 5);


            activity.settingService.save(voice_broadcast);
            activity.settingService.save(dial_sound);
            activity.settingService.save(lock_screen);
            activity.settingService.save(cover_pic);
            activity.settingService.save(picked_color);*/



        }
    }
}
