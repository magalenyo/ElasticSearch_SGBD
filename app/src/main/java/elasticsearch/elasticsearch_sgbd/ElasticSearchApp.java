package elasticsearch.elasticsearch_sgbd;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ElasticSearchApp extends Application {

    static String API_BASE_URL = "http://451fe442.ngrok.io";

    private ElasticSearchAPI restAPI;

    @Override
    public void onCreate(){

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        restAPI = retrofit.create(ElasticSearchAPI.class);
    }

    public ElasticSearchAPI getAPI(){
        return restAPI;
    }
}
