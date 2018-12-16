package elasticsearch.elasticsearch_sgbd.rest;

import retrofit2.Call;
import retrofit2.http.*;
import elasticsearch.elasticsearch_sgbd.entity.*;

public interface ElasticSearchAPI {

    @GET("/persones/persona/{id}")
    Call<Persona> getPersona(@Path("id") int id);
}
