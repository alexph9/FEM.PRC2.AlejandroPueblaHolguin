package es.upm.miw.firebaselogin;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@SuppressWarnings("Unused")
interface CountryRESTAPIService {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    // https://restcountries.eu/rest/v2/name/España
    @GET("/rest/v2/name/{countryName}")
    Call<List<Country>> getCountryByName(@Path("countryName") String countryName);

}
