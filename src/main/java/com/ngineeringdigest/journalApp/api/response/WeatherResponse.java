package com.ngineeringdigest.journalApp.api.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    public class Current {

        public int temperature;

        @JsonProperty("weather_descriptions")
        public List<String> weatherDescriptions;

        public int feelslike;


    }


   
}