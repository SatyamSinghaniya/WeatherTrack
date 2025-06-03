package com.example.weathertrack;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository repo;
    private LiveData<List<WeatherEntity>> last7Days;

    public WeatherViewModel(@NonNull Application app) {
        super(app);
        repo = new WeatherRepository(app);
        last7Days = repo.getLast7Days();
    }

    public LiveData<List<WeatherEntity>> getLast7Days() {
        return last7Days;
    }

    public void refreshWeather(Context context, MutableLiveData<String> error) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                repo.refreshWeather(context);
            } catch (Exception e) {
                error.postValue("Failed to refresh: " + e.getMessage());
            }
        });
    }
}