package dvik.com.taskzy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import dvik.com.taskzy.R;
import dvik.com.taskzy.data.SituationContract;
import dvik.com.taskzy.data.SituationModel;
import dvik.com.taskzy.data.SituationProvider;
import dvik.com.taskzy.utils.Constants;
import dvik.com.taskzy.utils.CursorRecyclerViewAdapter;

/**
 * Created by Divya on 11/16/2016.
 */

public class SituationListAdapter extends CursorRecyclerViewAdapter<SituationListAdapter.SituationViewHolder> {

    private Context context;
    private Cursor cursor;


    public static class SituationViewHolder extends RecyclerView.ViewHolder {
        TextView situationTitle;
        LinearLayout itemContainer;

        public SituationViewHolder(View v) {
            super(v);
            situationTitle = (TextView) v.findViewById(R.id.situation_name);
            itemContainer = (LinearLayout) v.findViewById(R.id.layout_situation_item);
        }
    }

    public SituationListAdapter(Context context, Cursor cursor) {
        super(context,cursor);
        this.context = context;
    }

    @Override
    public SituationListAdapter.SituationViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_situation, parent, false);
        return new SituationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SituationViewHolder holder, final Cursor cursor, final int position) {

        final String name = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_NAME));
        final String head = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_HEADPHONE_STATE));
        final String weather = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_WEATHER_STATE));
        final String latitude = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_LATITUDE));
        final String longitude = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_LONGITUDE));
        final String activity = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_ACTIVITY));
        final String time = cursor.getString(cursor.getColumnIndex(SituationContract.SituationEntry.COLUMN_TIME));

        holder.situationTitle.setText(name);
        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("situationName",name);
                i.putExtra("situationHead",head);
                i.putExtra("situationWeather",weather);
                i.putExtra("situationLat",latitude);
                i.putExtra("situationLong",longitude);
                i.putExtra("situationAct",activity);
                i.putExtra("situationTime",time);
                ((Activity) context).setResult(Constants.SITUATION_REQUEST_CODE, i);
                ((Activity) context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
