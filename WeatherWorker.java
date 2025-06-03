package com.example.weathertrack;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONObject;
import java.io.InputStream;

public class WeatherWorker extends Worker {
    public WeatherWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            String json = AssetUtils.loadJSONFromAsset(getApplicationContext(), "mock_weather.json");
            JSONObject obj = new JSONObject(json);

            WeatherEntity weather = new WeatherEntity();
            weather.temperature = obj.getInt("temperature");
            weather.humidity = obj.getInt("humidity");
            weather.condition = obj.getString("condition");
            weather.timestamp = System.currentTimeMillis();

            AppDatabase.getInstance(getApplicationContext()).weatherDao().insert(weather);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }
}