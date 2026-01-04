package com.animesh.journal.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WeatherResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public Current current;

    @Data
    public class Current implements Serializable {

        public int temperature;

        @JsonProperty("weather_descriptions") //snakecase
        public List<String> weatherDescriptions; //camelcase

        public int feelslike;
    }
}


