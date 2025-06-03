package com.example.weathertrack;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_table")
public class WeatherEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int temperature;
    public int humidity;
    public String condition;
    public long timestamp;
}