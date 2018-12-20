package elasticsearch.elasticsearch_sgbd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import elasticsearch.elasticsearch_sgbd.entity.CategoriaDades;

import java.util.List;

public class RecyclerViewAdapterCategory extends RecyclerView.Adapter<RecyclerViewHoldersCategory> {

    private List<Object> llistaCategories;
    protected Context context;


    public RecyclerViewAdapterCategory(Context context, List<Object> newList, RecyclerView recView){
        this.llistaCategories = newList;
        this.context = context;
    }


    @Override
    public RecyclerViewHoldersCategory onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutViewHome = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent, false);
        RecyclerViewHoldersHomeCategory viewHolder = new RecyclerViewHoldersHomeCategory(layoutViewHome);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHoldersCategory holder, final int position) {
        CategoriaDades item = (CategoriaDades) llistaCategories.get(position);
        RecyclerViewHoldersHomeCategory rvdc = (RecyclerViewHoldersHomeCategory) holder;
        rvdc.nomCat.setText(item.nom);
        rvdc.nomCategoria = item.nom;
    }

    @Override
    public int getItemCount() {
        return this.llistaCategories.size();
    }
}
