package com.animesh.journal.services;

import com.animesh.journal.Cache.AppCache;
import com.animesh.journal.Entity.User;
import com.animesh.journal.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


//@Component
@Service //We can also use @Component but @Service gives better readibility to developer that this is a service containing business logic
public class WeatherService {

    @Autowired
    RestTemplate restTemplate; // this will allow us to send http request and get respones

    //@Value("${weather.api.key}")
    //public String KEY;

    //public static String API=""; //this the api endpoint we have got from the documentation page of the weather website
    @Autowired
    AppCache appCache;

    @Autowired
    RedisService redisService;

    public WeatherResponse getWeather(String city) {
        //HttpHeaders headers = new HttpHeaders();
        //headers.set("key","value");
        //User user=User.builder().userName("Animesh").password("123").build();
        //HttpEntity<User> httpEntity=new HttpEntity<>(user,headers);
        // ResponseEntity<WeatherResponse> response=restTemplate.exchange(final_api, HttpMethod.POST,httpEntity, WeatherResponse.class);

        //Like GET call to a external API we can also call POST by sending a HTTPEntity in the rewuest entity
        //Http Entiy contains body and headers.
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String final_api = appCache.APPCACHE.get("weather_api").replace("<city>", city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(final_api, HttpMethod.POST, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 300l);
            }
            return body;
        }
    }
}
