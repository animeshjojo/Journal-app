package com.animesh.journal.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WeatherResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Current current;

    @Data
    public class Current implements Serializable {

        private int temperature;

        @JsonProperty("weather_descriptions") //snakecase
        private List<String> weatherDescriptions; //camelcase

        private int feelslike;
    }
}


