/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcher3;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;


public class DynamicGrid {
    @SuppressWarnings("unused")
    private static final String TAG = "DynamicGrid";

    private DeviceProfile mProfile;
    private float mMinWidth;
    private float mMinHeight;

    // This is a static that we use for the default icon size on a 4/5-inch phone
    static float DEFAULT_ICON_SIZE_DP = 100;
    static float DEFAULT_ICON_SIZE_PX = 0;

    public static float dpiFromPx(int size, DisplayMetrics metrics){
        float densityRatio = (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        return (size / densityRatio);
    }
    public static int pxFromDp(float size, DisplayMetrics metrics) {
        return (int) Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                size, metrics));
    }
    public static int pxFromSp(float size, DisplayMetrics metrics) {
        return (int) Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                size, metrics));
    }

    public DynamicGrid(Context context, Resources resources,
                       int minWidthPx, int minHeightPx,
                       int widthPx, int heightPx,
                       int awPx, int ahPx) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        ArrayList<DeviceProfile> deviceProfiles =
                new ArrayList<DeviceProfile>();
        boolean hasAA = !LauncherAppState.isDisableAllApps();
        DEFAULT_ICON_SIZE_PX = pxFromDp(DEFAULT_ICON_SIZE_DP, dm);
        // Our phone profiles include the bar sizes in each orientation
        deviceProfiles.add(new DeviceProfile("Super Short Stubby",
                255, 300,  2, 3,  48, 13, (hasAA ? 3 : 2), 48));
        deviceProfiles.add(new DeviceProfile("Shorter Stubby",
                255, 400,  3, 2,  48, 13, (hasAA ? 3 : 2), 48));
        deviceProfiles.add(new DeviceProfile("Short Stubby",
                275, 420,  3, 2,  48, 13, (hasAA ? 3 : 2), 48));
        deviceProfiles.add(new DeviceProfile("Stubby",
                255, 450,  3, 2,  48, 13, (hasAA ? 3 : 2), 48));
        deviceProfiles.add(new DeviceProfile("Nexus S",
                296, 491.33f,  3, 2,  48, 13, (hasAA ? 3 : 2), 48));
        deviceProfiles.add(new DeviceProfile("Nexus 4",
                335, 567,  3, 2,  DEFAULT_ICON_SIZE_DP, 24, (hasAA ? 2 : 2), DEFAULT_ICON_SIZE_DP));
        deviceProfiles.add(new DeviceProfile("Nexus 5",
                359, 567,  3, 2,  DEFAULT_ICON_SIZE_DP, 24, (hasAA ? 3 : 2), DEFAULT_ICON_SIZE_DP));
        deviceProfiles.add(new DeviceProfile("Large Phone",
                406, 694,  3, 2,  DEFAULT_ICON_SIZE_DP, 24,  2, DEFAULT_ICON_SIZE_DP));
        // The tablet profile is odd in that the landscape orientation
        // also includes the nav bar on the side
        deviceProfiles.add(new DeviceProfile("Nexus 7",
                575, 904,  3, 2,  DEFAULT_ICON_SIZE_DP, 24,  2, DEFAULT_ICON_SIZE_DP));
        // Larger tablet profiles always have system bars on the top & bottom
        deviceProfiles.add(new DeviceProfile("Nexus 10",
                727, 1207,  3, 2,  DEFAULT_ICON_SIZE_DP, 24,  2, DEFAULT_ICON_SIZE_DP));
        /*
        deviceProfiles.add(new DeviceProfile("Nexus 7",
                600, 960,  5, 5,  72, 14.4f,  5, 60));
        deviceProfiles.add(new DeviceProfile("Nexus 10",
                800, 1280,  5, 5,  80, 14.4f, (hasAA ? 7 : 6), 64));
         */
        deviceProfiles.add(new DeviceProfile("20-inch Tablet",
                1527, 2527,  3, 2,  DEFAULT_ICON_SIZE_DP, 24,  2, DEFAULT_ICON_SIZE_DP));
        mMinWidth = dpiFromPx(minWidthPx, dm);
        mMinHeight = dpiFromPx(minHeightPx, dm);
        mProfile = new DeviceProfile(context, deviceProfiles,
                mMinWidth, mMinHeight,
                widthPx, heightPx,
                awPx, ahPx,
                resources);
    }

    public DeviceProfile getDeviceProfile() {
        return mProfile;
    }

    public String toString() {
        return "-------- DYNAMIC GRID ------- \n" +
                "Wd: " + mProfile.minWidthDps + ", Hd: " + mProfile.minHeightDps +
                ", W: " + mProfile.widthPx + ", H: " + mProfile.heightPx +
                " [r: " + mProfile.numRows + ", c: " + mProfile.numColumns +
                ", is: " + mProfile.iconSizePx + ", its: " + mProfile.iconTextSizePx +
                ", cw: " + mProfile.cellWidthPx + ", ch: " + mProfile.cellHeightPx +
                ", hc: " + mProfile.numHotseatIcons + ", his: " + mProfile.hotseatIconSizePx + "]";
    }
}
