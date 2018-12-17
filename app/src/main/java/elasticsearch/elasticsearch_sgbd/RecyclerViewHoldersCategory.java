package elasticsearch.elasticsearch_sgbd;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerViewHoldersCategory extends RecyclerView.ViewHolder{
    public TextView nomCat;
    protected Context context;
    public String nom;


    public RecyclerViewHoldersCategory(View itemView) {
        super(itemView);
        context = itemView.getContext();
        nomCat = (TextView) itemView.findViewById(R.id.categoria_nom);
        nom = nomCat.getText().toString();

    }
}
