package elasticsearch.elasticsearch_sgbd;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import elasticsearch.elasticsearch_sgbd.R;
import elasticsearch.elasticsearch_sgbd.entity.Persona;
import elasticsearch.elasticsearch_sgbd.entity.Producte;
import elasticsearch.elasticsearch_sgbd.entity.Productes;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
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
        mAdapterData = new ArrayList<>();
        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();

        Intent intent = new Intent(this, Activity_Producte.class);
        intent.putExtra("ID", 1);
        startActivity(intent);
        //setDadesInicials();
        //setHomeScroll();
    }

    /*private void setDadesInicials(){
        Call<List<Productes>> call = api.nproductes(0,10);
        call.enqueue(new Callback<List<Productes>>() {
            @Override
            public void onResponse(Call<List<Productes>> call, Response<List<Productes>> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");
                    for(Producte p : response.body()){
                        mAdapterData.add(p._source);
                        recyclerViewAdapter.notifyItemInserted(mAdapterData.size());
                    }
                }
                else{
                    System.out.println("Not success1");
                }
            }

            @Override
            public void onFailure(Call<List<Productes>> call, Throwable t) {
                System.out.println("Not success2");
                System.out.println(t);
            }
        });
    }

    public void setHomeScroll(){
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
        System.out.println("arribo");
        Call<List<Producte>> call = api.nproductes(0,10);
        call.enqueue(new Callback<List<Producte>>() {
            @Override
            public void onResponse(Call<List<Producte>> call, Response<List<Producte>> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");
                    for(Producte p : response.body()){
                        mAdapterData.add(p._source);
                        recyclerViewAdapter.notifyItemInserted(mAdapterData.size());
                    }
                }
                else{
                    System.out.println("Not success1");
                }
            }

            @Override
            public void onFailure(Call<List<Producte>> call, Throwable t) {
                System.out.println("Not success2");
                System.out.println(t);
            }
        });
    }*/
}
