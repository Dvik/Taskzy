package dvik.com.taskzy.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dvik.com.taskzy.R;
import dvik.com.taskzy.data.SituationModel;

/**
 * Created by Divya on 11/16/2016.
 */

public class SituationListAdapter extends RecyclerView.Adapter<SituationListAdapter.SituationViewHolder> {

    private List<SituationModel> situationList;
    private Context context;


    public static class SituationViewHolder extends RecyclerView.ViewHolder {
        TextView situationTitle;
        CardView itemContainer;

        public SituationViewHolder(View v) {
            super(v);
            situationTitle = (TextView) v.findViewById(R.id.situation_title);
            itemContainer = (CardView) v.findViewById(R.id.layout_list);
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

        /*holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ArrayList<AwarenessFence> awarenessFences = new ArrayList<AwarenessFence>();

                    if(!TextUtils.isEmpty(situationList.get(position).getUserActivity()))
                    awarenessFences.add(DetectedActivityFence.during(DetectedActivityFence.STILL));

                    if(!TextUtils.isEmpty(situationList.get(position).getHeadPhoneState()))
                    awarenessFences.add(HeadphoneFence.during(HeadphoneState.PLUGGED_IN));


                    AwarenessFence stillWithHeadPhoneFence = AwarenessFence.and(awarenessFences);

                    Intent intent = new Intent(Constants.ACTION_FENCE);
                    PendingIntent fencePendingIntent = PendingIntent.getBroadcast(((Activity) context), 0, intent, 0);

                    TaskzyFenceReceiver mFenceBroadcastReceiver = new TaskzyFenceReceiver();
                    ((Activity) context).registerReceiver(mFenceBroadcastReceiver, new IntentFilter(Constants.ACTION_FENCE));

                    FenceUpdateRequest.Builder builder = new FenceUpdateRequest.Builder();
                    builder.addFence(Constants.IDLE_WITH_HEADPHONES_ON, stillWithHeadPhoneFence, fencePendingIntent);

                    Awareness.FenceApi.updateFences(googleApiClient, builder.build());
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return situationList.size();
    }
}
