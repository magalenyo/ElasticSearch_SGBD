package elasticsearch.elasticsearch_sgbd;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
public abstract class RecyclerViewHolders extends RecyclerView.ViewHolder{
    public TextView nom;
    public TextView marca;
    public TextView preu;
    public ImageView imageView;
    protected Context context;
    public int ID;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        context = itemView.getContext();
        nom = (TextView)itemView.findViewById(R.id.list_item_nom);
        marca = (TextView)itemView.findViewById(R.id.list_item_marca);
        preu = (TextView)itemView.findViewById(R.id.list_item_preu);
        imageView = (ImageView)itemView.findViewById(R.id.list_item_image);
    }
}

