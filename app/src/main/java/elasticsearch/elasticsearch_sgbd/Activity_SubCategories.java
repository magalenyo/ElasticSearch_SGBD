package elasticsearch.elasticsearch_sgbd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import elasticsearch.elasticsearch_sgbd.entity.Categoria;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_SubCategories extends AppCompatActivity {

    private String nomCat;
    private ElasticSearchApp app;
    private ElasticSearchAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);

        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();

        Intent myIntent = getIntent();
        nomCat = myIntent.getStringExtra("nom"); // <-- ?


        Call<Categoria> call = api.subcategories(nomCat);
        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");
                    System.out.println(response.body());
                }
                else{
                    System.out.println("Hi ha hagut un problema en la carrega de categories");
                }
            }

            @Override
            public void onFailure(Call<Categoria> call, Throwable t) {
                System.out.println("No s'ha rebut resposta del servidor");
                System.out.println(t);
            }
        });
    }
}
