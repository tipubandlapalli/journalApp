package net.engineeringdigest.journalapp.enums;


import lombok.Getter;

@Getter
public enum  Profiles {
    NEVER_USE_ME("NEVER_USE_ME_rely_only_on_finals");

    public static final String DEV_PROFILE = "dev";
    public static final String PROD_PROFILE = "prod";

    private final String value;

    Profiles(String value) {
        this.value = value;
    }

    // You can remove the unused getValues() method if it was for String[]
}