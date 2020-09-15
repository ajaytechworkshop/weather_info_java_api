package com.weatherapp.service;

import com.weatherapp.model.Weather;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService
{
    public Weather weatherInfo(String cityName);
}
