package net.engineeringdigest.journalApp.api_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherApi {
    private Location location;
    private Current current;

    @Data
    public static class Location{
        private String name;
        private String region;
        private String country;
    }
    @Data
    public static class Current {
        private Condition condition;
        @Data
        public static class Condition{
            public String text;
        }

        @JsonProperty("temp_c")
        private double tempC;

        @JsonProperty("wind_kph")
        private double windKph;

        @JsonProperty("feelslike_c")
        private double feelslikeC;
    }
}

