package elasticsearch.elasticsearch_sgbd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import elasticsearch.elasticsearch_sgbd.entity.Categoria;
import elasticsearch.elasticsearch_sgbd.entity.Categories;
import elasticsearch.elasticsearch_sgbd.entity.Producte;
import elasticsearch.elasticsearch_sgbd.entity.Productes;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Productes_Categoria extends AppCompatActivity {


    private ElasticSearchApp app;
    private ElasticSearchAPI api;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private RecyclerView rcNavigator;

    private List<Object> mAdapterData;
    private Handler handler;
    private final int size = 8;
    private int from;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    String nomCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapterData = new ArrayList<>();
        handler = new Handler();
        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();
        from = 0;


        Intent myIntent = getIntent();
        nomCat = myIntent.getStringExtra("nomSubCat");


        updateAdapterData(nomCat);
        setHomeScroll();
    }


    public void setHomeScroll(){
        linearLayoutManager = new LinearLayoutManager(Productes_Categoria.this);
        recyclerView = (RecyclerView)findViewById(R.id.RecyclerView_HomeItems);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(
                Productes_Categoria.this, mAdapterData, recyclerView
        );
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateAdapterData(nomCat);
                        recyclerViewAdapter.setLoaded();
                    }
                }, 2000);
            }
        });
    }

    private void updateAdapterData(String nomCat) {

        Map<String, String> filres = new HashMap<String, String>();
        filres.put("q", "altrecategoria : "+nomCat);

        System.out.println(nomCat);

        Call<Productes> call = api.productesCategoria(from,size,filres);
        call.enqueue(new Callback<Productes>() {
            @Override
            public void onResponse(Call<Productes> call, Response<Productes> response) {
                if(response.isSuccessful()){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
