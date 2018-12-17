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
    private final int size = 8;
    private int from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapterData = new ArrayList<>();
        handler = new Handler();
        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();
        from = 0;


        updateAdapterData();
        setHomeScroll();
    }

    private void setDadesInicials(){
        Call<Productes> call = api.nproductes(from,size);
        call.enqueue(new Callback<Productes>() {
            @Override
            public void onResponse(Call<Productes> call, Response<Productes> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");

                    for(Producte p : response.body().hits.hits){
                        mAdapterData.add(p._source);
                        recyclerViewAdapter.notifyItemInserted(mAdapterData.size());
                    }
                }
                else{
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
        Call<Productes> call = api.nproductes(from,size);
        call.enqueue(new Callback<Productes>() {
            @Override
            public void onResponse(Call<Productes> call, Response<Productes> response) {
                if(response.isSuccessful()){
                    System.out.println("Success");
                    for(Producte p : response.body().hits.hits){
                        mAdapterData.add(p._source);
                        recyclerViewAdapter.notifyItemInserted(mAdapterData.size());
                    }
                }
                else{
                    System.out.println("Not success1");
                }
            }

            @Override
            public void onFailure(Call<Productes> call, Throwable t) {
                System.out.println("Not success2");
                System.out.println(t);
            }
        });
        from+=8;
    }
}
