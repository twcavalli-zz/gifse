package gifse.thomas.com.br.gifse.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gifse.thomas.com.br.gifse.R;
import gifse.thomas.com.br.gifse.ui.activity.HistoryActivity;

/**
 * Created by Thomas on 11/07/2017.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.myViewHolder> {
    private ArrayList<String> mDataset;
    private LayoutInflater inflater;
    private Context context;

    public SearchHistoryAdapter(Context ctx, ArrayList myDataset) {
        this.context = ctx;
        inflater = LayoutInflater.from(ctx);
        mDataset = myDataset;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder viewHolder, int i) {
        viewHolder.word.setText(mDataset.get(i).toString());

        // When item clicked, return the word back to Search activity
        final String item = mDataset.get(i).toString();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("search_word",item);
                ((HistoryActivity) context).setResult(Activity.RESULT_OK,returnIntent);
                ((HistoryActivity) context).finish();
            }
        });
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
        TextView word;
        public myViewHolder(View itemView) {
            super(itemView);
            word = (TextView) itemView.findViewById(R.id.history_search_row);
        }
    }
}
