package gifse.thomas.com.br.gifse.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import gifse.thomas.com.br.gifse.R;

/**
 * Created by Thomas on 11/07/2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.myViewHolder> {
    private ArrayList<String> mDataset;
    private LayoutInflater inflater;
    private Context context;

    private int lastPosition = -1;

    public CardAdapter(Context context, ArrayList myDataset) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataset = myDataset;
    }

    @Override
    public CardAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_adapter, parent, false);
        CardAdapter.myViewHolder holder = new CardAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.myViewHolder viewHolder, int i) {
        try {
            // Download image
            Glide.with(context)
                    .asGif()
                    .load(mDataset.get(i).toString())
                    .into(viewHolder.image_media);
        } catch (Exception e) { }
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView myRecyclerView) {
        super.onAttachedToRecyclerView(myRecyclerView);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView image_media;
        public myViewHolder(View itemView) {
            super(itemView);
            image_media = (ImageView) itemView.findViewById(R.id.img_media);

        }
    }
}