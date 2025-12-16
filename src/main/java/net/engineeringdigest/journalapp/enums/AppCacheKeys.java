package net.engineeringdigest.journalapp.enums;

import lombok.Getter;

@Getter
public enum AppCacheKeys {
    STRING("WEATHER_API_STRING"),
    CITY("WEATHER_API_CITY"),
    KEY("WEATHER_API_KEY"),

    WEATHER_OF_("WEATHER_OF_");
    private final String value;

    AppCacheKeys(String value) {
        this.value = value;
    }
}
