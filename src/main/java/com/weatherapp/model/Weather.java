package com.weatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather
{
    //WEATHER DESCRIPTION e.g SCATTERED CLOUDS
    @JsonProperty("condition")
    private String condition;

    //THE ACTUAL TEMPERATURE OF THE CITY IN CELSIUS
    @JsonProperty("temperature")
    private float temperature;

    //SPEED OF THE WIND IN Km/H
    @JsonProperty("wind_speed")
    private float windSpeed;
}
