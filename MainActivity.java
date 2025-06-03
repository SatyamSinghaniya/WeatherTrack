package com.example.weathertrack;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private WeatherViewModel viewModel;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorText = findViewById(R.id.error_message);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        MutableLiveData<String> error = new MutableLiveData<>();

        viewModel.getLast7Days().observe(this, list -> {
            // update recycler adapter
        });

        error.observe(this, message -> errorText.setText(message));

        findViewById(R.id.btn_refresh).setOnClickListener(v -> {
            viewModel.refreshWeather(this, error);
        });

        scheduleWeatherSync();
    }

    public void scheduleWeatherSync() {
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                WeatherWorker.class, 6, TimeUnit.HOURS).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "weather_sync", ExistingPeriodicWorkPolicy.KEEP, request);
    }
}