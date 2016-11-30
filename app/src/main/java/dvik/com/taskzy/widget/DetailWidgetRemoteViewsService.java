package dvik.com.taskzy.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import dvik.com.taskzy.R;
import dvik.com.taskzy.data.SituationContract;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                final long identityToken = Binder.clearCallingIdentity();

                /*data = getContentResolver().query(SituationContract.SituationEntry.CONTENT_URI,
                        new String[]{SituationContract.SituationEntry.COLUMN_NAME},
                        SituationContract.SituationEntry.COLUMN_ACTION + "!= ? AND"
                                + SituationContract.SituationEntry.COLUMN_CHECKED + "== ?", new String[]{"", "1"},
                        null);*/

                data = getContentResolver().query(SituationContract.SituationEntry.CONTENT_URI,
                        new String[]{SituationContract.SituationEntry.COLUMN_ID, SituationContract.SituationEntry.COLUMN_NAME},
                        SituationContract.SituationEntry.COLUMN_ACTION + "!= ? AND "
                                + SituationContract.SituationEntry.COLUMN_CHECKED + "== ?", new String[]{"", "1"},
                        null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_list_item);

                String name = data.getString(data.getColumnIndex(SituationContract.SituationEntry.COLUMN_NAME));

                views.setTextViewText(R.id.situation_name, name);

                Bundle extras = new Bundle();
                extras.putString(SituationContract.SituationEntry.COLUMN_NAME, name);

                final Intent intent = new Intent();
                intent.putExtras(extras);
                views.setOnClickFillInIntent(R.id.widget_item, intent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return Long.parseLong(data.getString(data.getColumnIndex(SituationContract.SituationEntry.COLUMN_ID)));
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}