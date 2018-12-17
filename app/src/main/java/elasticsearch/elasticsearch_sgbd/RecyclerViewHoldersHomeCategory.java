package elasticsearch.elasticsearch_sgbd;

import android.content.Intent;
import android.view.View;

public class RecyclerViewHoldersHomeCategory extends RecyclerViewHoldersCategory implements View.OnClickListener{

    public RecyclerViewHoldersHomeCategory(View itemView) {
        super(itemView);
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, Activity_SubCategories.class);
        intent.putExtra("nom", nom);
        context.startActivity(intent);
    }
}
