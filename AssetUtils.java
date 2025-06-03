package com.example.weathertrack;

import android.content.Context;
import java.io.InputStream;

public class AssetUtils {
    public static String loadJSONFromAsset(Context context, String filename) throws java.io.IOException {
        InputStream is = context.getAssets().open(filename);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }
}