package com.weatherapp.controller;

import com.weatherapp.model.Weather;
import com.weatherapp.service.WeatherService;
import com.weatherapp.util.UrlMappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlMappings.APP_ROOT)
@Slf4j
public class WeatherInformationController
{
    private final WeatherService openWeatherMapService;

    WeatherInformationController(WeatherService openWeatherMapService){
        this.openWeatherMapService = openWeatherMapService;
    }

    @GetMapping(UrlMappings.CITY)
    public ResponseEntity<Weather> getCityWeather(@RequestParam(required = true,name = "name") String cityName){
        return ResponseEntity
                .ok(openWeatherMapService.weatherInfo(cityName));
    }
}
