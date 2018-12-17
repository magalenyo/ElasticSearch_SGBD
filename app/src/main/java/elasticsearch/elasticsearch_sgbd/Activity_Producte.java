package elasticsearch.elasticsearch_sgbd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import elasticsearch.elasticsearch_sgbd.entity.Persona;
import elasticsearch.elasticsearch_sgbd.entity.Producte;
import elasticsearch.elasticsearch_sgbd.entity.ProducteDades;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Producte extends AppCompatActivity {

    private int ID;
    private ElasticSearchApp app;
    private ElasticSearchAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producte);

        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();

        Intent myIntent = getIntent();
        ID = myIntent.getIntExtra("ID", -1);


        Call<Producte> call = api.getProducte(ID);
        call.enqueue(new Callback<Producte>() {
            @Override
            public void onResponse(Call<Producte> call, Response<Producte> response) {
                if(response.isSuccessful()){
                    Producte dades = response.body();
                    System.out.println("Success");
                    assignarValors(dades._source);
                }
                else{
                    System.out.println("Not success1");
                }
            }

            @Override
            public void onFailure(Call<Producte> call, Throwable t) {
                System.out.println("Not success2");
                System.out.println(t);
            }
        });
    }

    private void assignarValors(ProducteDades dades) {
        ImageView mevaVista = (ImageView) findViewById(R.id.producte_image);
        Picasso.get().load(dades.imageurl).resize(300,300).centerCrop().into(mevaVista);
        TextView nom = (TextView) findViewById(R.id.producte_nom);
        nom.setText(dades.nom);
        TextView preu = (TextView) findViewById(R.id.producte_preu);
        preu.setText(Float.toString(dades.preu)+"e");
        TextView preuenviament = (TextView) findViewById(R.id.producte_preuEnviament);
        if(dades.preuenviament == 0) preuenviament.setText("Gratuït");
        else preuenviament.setText(Float.toString(dades.preuenviament)+"e");
        TextView condicions = (TextView) findViewById(R.id.producte_condicions);
        condicions.setText("Condicions: "+dades.condicions);
        TextView disponibilitat = (TextView) findViewById(R.id.producte_disponibilitat);
        disponibilitat.setText("Stock: "+dades.disponibilitat);
        TextView fabricant = (TextView) findViewById(R.id.producte_fabricant);
        fabricant.setText("Fabricant: "+dades.fabricant);
        TextView marca = (TextView) findViewById(R.id.producte_marca);
        marca.setText("Marca: "+dades.marca);
        TextView data = (TextView) findViewById(R.id.producte_dataInsercio);
        data.setText(dades.datainsercio);
    }

}
