package dvik.com.taskzy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dvik.com.taskzy.R;
import dvik.com.taskzy.data.SituationModel;
import dvik.com.taskzy.utils.Constants;

/**
 * Created by Divya on 11/16/2016.
 */

public class SituationListAdapter extends RecyclerView.Adapter<SituationListAdapter.SituationViewHolder> {

    private List<SituationModel> situationList;
    private Context context;


    public static class SituationViewHolder extends RecyclerView.ViewHolder {
        TextView situationTitle;
        LinearLayout itemContainer;

        public SituationViewHolder(View v) {
            super(v);
            situationTitle = (TextView) v.findViewById(R.id.situation_name);
            itemContainer = (LinearLayout) v.findViewById(R.id.layout_situation_item);
        }
    }

    public SituationListAdapter(List<SituationModel> situationList, Context context) {
        this.situationList = situationList;
        this.context = context;
    }

    @Override
    public SituationListAdapter.SituationViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_situation, parent, false);
        return new SituationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SituationViewHolder holder, final int position) {

        holder.situationTitle.setText(situationList.get(position).getName());
        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("situation",situationList.get(position));
                ((Activity) context).setResult(Constants.SITUATION_REQUEST_CODE, i);
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return situationList.size();
    }
}
