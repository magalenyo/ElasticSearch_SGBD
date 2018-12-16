package elasticsearch.elasticsearch_sgbd;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import elasticsearch.elasticsearch_sgbd.entity.Producte;
import elasticsearch.elasticsearch_sgbd.entity.ProducteDades;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<Object> itemList;
    protected Context context;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public RecyclerViewAdapter(Context context, List<Object> itemList, RecyclerView recyclerView) {
        this.itemList = itemList;
        this.context = context;

        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                        if(onLoadMoreListener != null){
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? 1 : 0;
    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        if(viewType == 1){
                View layoutViewHome = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                RecyclerViewHoldersHome viewHolderHome = new RecyclerViewHoldersHome(layoutViewHome);
                viewHolder = viewHolderHome;
        }else{
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ProgressViewHolder(layoutView);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        if(holder instanceof RecyclerViewHolders){
            ProducteDades item = (ProducteDades) itemList.get(position);
            RecyclerViewHoldersHome rvh = (RecyclerViewHoldersHome)holder;
            rvh.ID = item.codi;
            rvh.marca.setText(item.marca);
            rvh.nom.setText(item.nom);
            rvh.preu.setText(Float.toString(item.preu));
            Picasso.get().load(item.imageURL).resize(180,180).centerCrop().into(rvh.imageView);
        }
        else{
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoad(){
        loading = false;
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoaded() {
        loading = false;
    }
}
