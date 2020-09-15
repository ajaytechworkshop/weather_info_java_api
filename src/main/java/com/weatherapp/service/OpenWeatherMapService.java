package com.weatherapp.service;

import com.weatherapp.model.OpenWeatherMapResponse;
import com.weatherapp.model.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.ValidationException;

@Service
@Slf4j
public class OpenWeatherMapService implements WeatherService
{
    private final RestTemplate restTemplate;

    @Value("${openweathermap.url}")
    private String openWeatherMapUrl;

    @Value("${openweathermap.apiKey}")
    private String apiKey;

    OpenWeatherMapService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Weather weatherInfo(String cityName)
    {
        //Validate city name. The city name accepts only alphabets and no numbers
        if(!cityName.matches("^[a-zA-Z ]*$")){
            throw new ValidationException("Invalid City Name");
        }

        log.info("Fetching weather information for the city : {}",cityName);

        //Build the Request URL with Query params city name and api key
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(openWeatherMapUrl)
                .queryParam("q",cityName)
                .queryParam("APPID",apiKey)
                .queryParam("units","metric");

        log.info("Url String : {}",uriBuilder.toUriString());

        //Fetch Weather information from OpenWeatherMap
        ResponseEntity<OpenWeatherMapResponse> openMapWeatherResponse = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                OpenWeatherMapResponse.class
                );

        if(openMapWeatherResponse.getStatusCode() == HttpStatus.OK){

            log.info("Weather details fetched successfully : {}",openMapWeatherResponse.getBody());
            OpenWeatherMapResponse openMapWeatherInfo = openMapWeatherResponse.getBody();

            //Weather details are returned from open map as a List.
            assert !openMapWeatherInfo.getWeather().isEmpty() : "Weather details could not be fetched";

            //Get the weather information from the first list item
            OpenWeatherMapResponse.Weather weatherInfo = openMapWeatherInfo.getWeather().get(0);

            //Build weather response with the information fetched from open map
            return Weather.builder()
                    .condition(weatherInfo.getDescription())
                    .temperature(openMapWeatherInfo.getMain().getTemp())
                    .windSpeed(openMapWeatherInfo.getWind().getSpeed())
                    .build();
        }
        return null;
    }
}
