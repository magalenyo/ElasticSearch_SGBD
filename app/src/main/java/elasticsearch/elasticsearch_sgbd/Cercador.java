package elasticsearch.elasticsearch_sgbd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import elasticsearch.elasticsearch_sgbd.entity.Condicio;
import elasticsearch.elasticsearch_sgbd.entity.Condicions;
import elasticsearch.elasticsearch_sgbd.entity.ProducteDades;
import elasticsearch.elasticsearch_sgbd.entity.Productes;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cercador extends AppCompatActivity {

    private ElasticSearchApp app;
    private ElasticSearchAPI api;

    private ArrayList<String> llista;
    private Spinner spinner1,spinner2;
    private String condicioAConsultar;
    Map<String, String> opcions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cercador);
        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();
        opcions = new HashMap<String, String>();
        setSpinnerCondicions();
        setSpinnerPreus();

        Button button = (Button) findViewById(R.id.cercador_boto);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                obtenirDades();
            }
        });

    }

    private void setSpinnerCondicions() {
        spinner1 = (Spinner) findViewById(R.id.cercador_spinner);
        llista = new ArrayList<>();

        Call<Condicions> call = api.getCondicions();
        call.enqueue(new Callback<Condicions>() {
            @Override
            public void onResponse(Call<Condicions> call, Response<Condicions> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");
                    llista.add("");
                    for(Condicio c : response.body().hits.hits){
                        llista.add(c._source.nom);
                    }


                    setArray();
                    //obtenirDades();
                }
                else{
                    System.out.println("Not success1");
                }
            }

            @Override
            public void onFailure(Call<Condicions> call, Throwable t) {
                System.out.println("Not success2");
                System.out.println(t);
            }
        });
    }

    private void setSpinnerPreus(){
        spinner2 = (Spinner) findViewById(R.id.cercador_spinner_preu);
        ArrayList<String> llista2 = new ArrayList<>();
        llista2.add("asc");
        llista2.add("desc");
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,llista2);
        spinner2.setAdapter(adp);

    }

    private void setArray(){
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,llista);
        spinner1.setAdapter(adp);
        condicioAConsultar = spinner1.getSelectedItem().toString();

        //Set listener Called when the item is selected in spinner
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long arg3)
            {
                //condicioAConsultar = spinner.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });
    }

    private void obtenirDades() {
        condicioAConsultar = spinner1.getSelectedItem().toString();
        EditText stock = (EditText)findViewById(R.id.cercador_stock);
        EditText preu = (EditText)findViewById(R.id.cercador_preu);
        //Map<String, String> opcions = new HashMap<String, String>();
        //params.put("preu", "gte : 10");

        //opcions.put("sort", "preu:asc");

        opcions.put("sort","preu:"+spinner2.getSelectedItem().toString());

        //if()


        Call<Productes> call = api.getQueriedProducte(opcions);
        call.enqueue(new Callback<Productes>() {
            @Override
            public void onResponse(Call<Productes> call, Response<Productes> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");
                    //System.out.println(response);
                    System.out.println(response.body().hits.hits.get(0)._source.preu);

                }
                else{
                    System.out.println(response);
                    System.out.println("Not success1");
                }
            }

            @Override
            public void onFailure(Call<Productes> call, Throwable t) {
                System.out.println("Not success2");
                System.out.println(t);
            }
        });
    }
}
