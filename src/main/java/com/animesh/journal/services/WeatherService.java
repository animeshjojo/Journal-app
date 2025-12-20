package com.animesh.journal.services;

import com.animesh.journal.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    RestTemplate restTemplate; // this will allow us to send http request and get respones
    public static final String KEY="b45e9cca79df3ae436161109f1bb6e41";

    public static String API="http://api.weatherstack.com/current?access_key=KEY&query=CITY"; //this the api endpoint we have got from the documentation page of the weather website

    public WeatherResponse getWeather(String city){
        String final_api=API.replace("KEY",KEY).replace("CITY",city);
        ResponseEntity<WeatherResponse> response=restTemplate.exchange(final_api, HttpMethod.GET,null, WeatherResponse.class);
        return response.getBody();
    }
}
