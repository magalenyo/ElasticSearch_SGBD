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
import android.widget.TextView;
import android.widget.Toast;
import elasticsearch.elasticsearch_sgbd.entity.Categoria;
import elasticsearch.elasticsearch_sgbd.entity.Categories;
import elasticsearch.elasticsearch_sgbd.entity.Producte;
import elasticsearch.elasticsearch_sgbd.entity.Productes;
import elasticsearch.elasticsearch_sgbd.rest.ElasticSearchAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ElasticSearchApp app;
    private ElasticSearchAPI api;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private RecyclerView rcNavigator;
    private RecyclerViewAdapterCategory rvc;

    private List<Object> mAdapterData;
    private List<Object> categories;
    private Handler handler;
    private final int size = 8;
    private int from;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapterData = new ArrayList<>();
        categories = new ArrayList<>();
        handler = new Handler();
        app = (ElasticSearchApp) this.getApplication();
        api = app.getAPI();
        from = 0;


        TextView text = (TextView)findViewById(R.id.main_linkCercador);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Cercador.class);
                startActivity(intent);
            }
        });

        setNavigationDrawer();
        updateAdapterData();
        setHomeScroll();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Open,R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navView = (NavigationView)findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(this);
        View parentView = navView.getHeaderView(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rcNavigator = (RecyclerView) findViewById(R.id.RecyclerView_NavMenu);
        rcNavigator.setLayoutManager(linearLayoutManager);

        //System.out.println("--------------------------------------");
        //System.out.println("Fent call de categories");
        Call<Categories> call = api.categories();
        call.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if(response.isSuccessful()){
                    //System.out.println("Response rebuda de categories");
                   // System.out.println("Body:"+response.body().hits.hits);
                    for(Categoria p : response.body().hits.hits){
                        //System.out.println("Categoria _source: "+p._source.nom);
                        categories.add(p._source);
                    }
                    //System.out.println("Categories size: "+categories.size());
                    rvc = new RecyclerViewAdapterCategory(MainActivity.this, categories,rcNavigator);
                    rcNavigator.setAdapter(rvc);
                }
                else{
                    System.out.println("No s'ha pogut obtenir el llistar de categories principals");
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                System.out.println("Failure");
                System.out.println(t);
            }
        });



        //SharedPreferences mSettings = getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        return true;
    }
}
