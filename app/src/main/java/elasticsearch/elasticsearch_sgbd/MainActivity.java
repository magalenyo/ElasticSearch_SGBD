package elasticsearch.elasticsearch_sgbd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import elasticsearch.elasticsearch_sgbd.R;
import elasticsearch.elasticsearch_sgbd.entity.Persona;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ElasticSearchApp app;
    private ElasticSearchAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();

        Call<Persona> call = api.getPersona(1);
        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                if(response.isSuccessful()){
                    Persona dades = response.body();
                    System.out.println("Success1");
                    assignarValors(dades);
                }
                else{
                    System.out.println("not success1");
                }
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                System.out.println("not success2");
                System.out.println(t);
            }
        });
    }

    public void assignarValors(Persona dades){
        System.out.println("SOC:" + dades._source.nom);
    }
}
