package elasticsearch.elasticsearch_sgbd;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import elasticsearch.elasticsearch_sgbd.R;
import elasticsearch.elasticsearch_sgbd.entity.Persona;
import elasticsearch.elasticsearch_sgbd.entity.Producte;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ElasticSearchApp app;
    private ElasticSearchAPI api;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Object> mAdapterData;
    private Handler handler;
    private final int N_ITEMS_ON_LOAD = 5;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();

        setDades();
    }

    public void assignarValors(Persona dades){
        System.out.println("SOC:" + dades._source.nom);
    }

    public void setDades(){
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_HomeItems);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(
                MainActivity.this, mAdapterData, recyclerView
        );
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateAdapterData();
                        recyclerViewAdapter.setLoaded();
                    }
                }, 2000);
            }
        });
    }

    private void updateAdapterData() {
        Call<List<Producte>> call = api.nproductes(0,10);
        call.enqueue(new Callback<List<Producte>>() {
            @Override
            public void onResponse(Call<List<Producte>> call, Response<List<Producte>> response) {
                for(Producte p : response.body()){
                    mAdapterData.add(p._source);
                    recyclerViewAdapter.notifyItemInserted(mAdapterData.size());
                }

            }

            @Override
            public void onFailure(Call<List<Producte>> call, Throwable t) {
            }
        });
    }
}
