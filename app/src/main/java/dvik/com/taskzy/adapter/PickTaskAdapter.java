package dvik.com.taskzy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import dvik.com.taskzy.R;
import dvik.com.taskzy.utils.Constants;

/**
 * Created by Divya on 11/26/2016.
 */

public class PickTaskAdapter extends RecyclerView.Adapter<PickTaskAdapter.TaskViewHolder> {

    private List<ApplicationInfo> appsList;
    private PackageManager packageManager;
    private Context context;


    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemContainer;
        TextView appName;
        ImageView appIcon;

        public TaskViewHolder(View v) {
            super(v);
            itemContainer = (LinearLayout) v.findViewById(R.id.layout_app_item);
            appName = (TextView) v.findViewById(R.id.app_name);
            appIcon = (ImageView) v.findViewById(R.id.app_icon);
        }
    }

    public PickTaskAdapter(Context context, List<ApplicationInfo> appsList) {
        this.appsList = appsList;
        this.context = context;
        packageManager = context.getPackageManager();
    }

    @Override
    public PickTaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_app, parent, false);
        return new PickTaskAdapter.TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PickTaskAdapter.TaskViewHolder holder, final int position) {
        final ApplicationInfo applicationInfo = appsList.get(position);
        if (null != applicationInfo) {
            holder.appName.setText(applicationInfo.loadLabel(packageManager));
            holder.appIcon.setImageDrawable(applicationInfo.loadIcon(packageManager));
            holder.itemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();

                    Bitmap bitmap = ((BitmapDrawable) applicationInfo.loadIcon(packageManager)).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] b = baos.toByteArray();

                    i.putExtra("appName", applicationInfo.loadLabel(packageManager));
                    i.putExtra("appIcon", b);
                    i.putExtra("appPackage", applicationInfo.packageName);

                    ((Activity) context).setResult(Constants.TASK_REQUEST_CODE, i);
                    ((Activity) context).finish();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }
}
