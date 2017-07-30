package com.ews.fitnessmobile.api;

/**
 * Created by wallace on 13/07/17.
 */
public class APIUtils {

    private APIUtils(){}

    public static final String BASE_URL = "http://www.mocky.io";

    public static FitnessAPI getFitnessAPI() {
        return RetrofitClient.getClient(BASE_URL).create(FitnessAPI.class);
    }

    public static LoginAPI getLoginAPI() {
        return RetrofitClient.getClient(BASE_URL).create(LoginAPI.class);
    }

}
