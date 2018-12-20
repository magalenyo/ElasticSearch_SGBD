package elasticsearch.elasticsearch_sgbd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.flexbox.FlexboxLayout;
import elasticsearch.elasticsearch_sgbd.entity.Categoria;
import elasticsearch.elasticsearch_sgbd.entity.Categories;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_SubCategories extends AppCompatActivity {

    private String nomCat;
    private ElasticSearchApp app;
    private ElasticSearchAPI api;
    private Categoria actual;
    private List<String> subcategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);

        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();

        Intent myIntent = getIntent();
        nomCat = myIntent.getStringExtra("nom");


        estableixCategoriaActual(nomCat);


    }

    private void estableixCategoriaActual(String nomCat){
        Call<Categoria> call = api.subcategories(nomCat);
        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                if(response.isSuccessful()){
                    System.out.println("SuccessCategories");
                    System.out.println(response.body());
                    actual = response.body();

                    subcategories = Arrays.asList(actual._source.subcategories.split(","));

                    estableixFlowBox(subcategories);

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

    private void estableixFlowBox(List<String> subcategories) {

        /* Flexbox */
        FlexboxLayout panell_casting = (FlexboxLayout) findViewById(R.id.panell_subcategories);
        for (final String bp : subcategories) {
            int i=0;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView btn = new TextView(this);
            btn.setTextSize(25);
            btn.setId(i);
            btn.setBackgroundColor(Color.CYAN); // 0xAARRGGBB
            final int id_ = btn.getId();
            btn.setText(bp);
            params.setMargins(20,10,10,20);
            panell_casting.addView(btn, params);
            btn = ((TextView) findViewById(id_));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent prod_cat = new Intent(view.getContext(), Productes_Categoria.class);
                    prod_cat.putExtra("nomSubCat", bp);
                    System.out.println();
                    System.out.println("Text tocat "+bp);
                    System.out.println();
                    startActivity(prod_cat);
                }
            });
            i++;
        }
    }
}
