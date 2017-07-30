package com.ews.fitnessmobile.api;

import com.ews.fitnessmobile.model.Treino;
import com.ews.fitnessmobile.model.Unidade;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by wallace on 13/07/17.
 */
public interface FitnessAPI {

    //@GET("/v2/5967dccd110000061cb6c329")
    @GET("/v2/596d1d811000004b057e2390")
    Observable<List<Unidade>> getUnidades();

    @GET("/v2/undefined")
    Observable<Treino> getRotinaA();

    @GET("/v2/undefined")
    Observable<Treino> getRotinaB();

    @GET("/v2/undefined")
    Observable<Treino> getRotinaC();

}
