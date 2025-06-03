package com.example.weathertrack;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import java.util.List;

public class WeatherRepository {
    private WeatherDao dao;

    public WeatherRepository(Application app) {
        dao = AppDatabase.getInstance(app).weatherDao();
    }

    public LiveData<List<WeatherEntity>> getLast7Days() {
        return dao.getLast7Days();
    }

    public List<WeatherEntity> getDayDetails(long start, long end) {
        return dao.getDayDetails(start, end);
    }

    public void refreshWeather(Context context) throws Exception {
        String json = AssetUtils.loadJSONFromAsset(context, "mock_weather.json");
        JSONObject obj = new JSONObject(json);

        WeatherEntity weather = new WeatherEntity();
        weather.temperature = obj.getInt("temperature");
        weather.humidity = obj.getInt("humidity");
        weather.condition = obj.getString("condition");
        weather.timestamp = System.currentTimeMillis();
        dao.insert(weather);
    }
}