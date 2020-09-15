package com.weatherapp.model;

import lombok.Getter;

import java.util.List;

@Getter
public class OpenWeatherMapResponse
{
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Sys sys;
    private String timeZone;
    private String id;
    private String name;
    private String cod;

    @Getter
    public static class Coord{
        private long lon;
        private long lat;
    }

    @Getter
    public static class Weather{
        private String id;
        private String main;
        private String description;
        private String icon;
    }

    @Getter
    public static class Main{
        private float temp;
        private float feels_like;
        private float temp_min;
        private float temp_max;
        private float pressure;
        private float humidity;
        private float sea_level;
        private float grnd_level;
    }

    @Getter
    public static class Wind{
        private float speed;
        private float deg;
    }

    @Getter
    public static class Clouds{
        private String all;
    }

    @Getter
    public static class Sys{
        private String country;
        private String sunrise;
        private String sunset;
    }
}
