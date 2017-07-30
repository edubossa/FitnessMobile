package com.ews.fitnessmobile.api;

import com.ews.fitnessmobile.model.Login;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by wallace on 25/07/17.
 */

public interface LoginAPI {

    @GET("/v2/58b9b1740f0000b614f09d2f")
    Observable<Login> getLogin();

}
