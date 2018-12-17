package elasticsearch.elasticsearch_sgbd;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import elasticsearch.elasticsearch_sgbd.entity.Producte;

public class RecyclerViewHoldersHome extends RecyclerViewHolders implements View.OnClickListener{


    public RecyclerViewHoldersHome(View itemView) {
        super(itemView);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, Activity_Producte.class);
        intent.putExtra("ID", ID);
        context.startActivity(intent);
    }
}
