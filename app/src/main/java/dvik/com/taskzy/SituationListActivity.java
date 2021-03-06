package dvik.com.taskzy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import dvik.com.taskzy.adapter.SituationListAdapter;
import dvik.com.taskzy.data.SituationContract;
import dvik.com.taskzy.utils.PrefManager;

public class SituationListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SituationListAdapter situationListAdapter;
    PrefManager prefManager;
    ContentValues contentValues[];
    Cursor cursor;

    private static final int CURSOR_LOADER_ID = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (prefManager.getShouldAddDefaultData()) {
            addDefaultDataToDatabase();
            prefManager.setShouldAddDefaultData(false);
        }

        situationListAdapter = new SituationListAdapter(SituationListActivity.this, cursor);
        recyclerView.setAdapter(situationListAdapter);


    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.content_situation_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        prefManager = new PrefManager(SituationListActivity.this);
        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, SituationListActivity.this);

    }

    private void addDefaultDataToDatabase() {
        contentValues = new ContentValues[10];
        addToContentValue(0, "Bicycling with Headphones plugged in", getString(R.string.headphone_plugged), "", "", "","", getString(R.string.on_bicycle), "");
        addToContentValue(1, "In Vehicle with Headphones plugged in", getString(R.string.headphone_plugged), "", "", "", "",getString(R.string.in_vehicle), "");
        addToContentValue(2, "In Vehicle", "", "", "", "","", getString(R.string.in_vehicle), "");
        addToContentValue(3, "Walking on a clear day","" , getString(R.string.weather_clear), "", "", "",getString(R.string.walking), "");
        addToContentValue(4, "Running on a rainy day", "", getString(R.string.weather_rainy), "", "", "",getString(R.string.running), "");
        addToContentValue(5, "Walking", "", "", "", "", "",getString(R.string.walking), "");
        addToContentValue(6, "Headphones Plugged and Walking", getString(R.string.headphone_plugged), "", "", "", "",getString(R.string.walking), "");
        addToContentValue(7, "Headphones Plugged and Still", getString(R.string.headphone_plugged), "", "", "", "",getString(R.string.still), "");
        addToContentValue(8, "Headphones Un-Plugged", getString(R.string.headphone_unplugged), "", "", "", "","", "");
        addToContentValue(9, "Headphones Plugged", getString(R.string.headphone_plugged), "", "", "", "", "","");

        getContentResolver().bulkInsert(SituationContract.SituationEntry.CONTENT_URI,
                contentValues);
    }

    private void addToContentValue(int i, String name, String headphone, String weather, String lat, String lon, String place, String activity, String time) {
        contentValues[i] = new ContentValues();
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_NAME, name);
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_HEADPHONE_STATE, headphone);
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_WEATHER_STATE, weather);
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_LATITUDE, lat);
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_LONGITUDE, lon);
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_PLACE, place);
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_ACTIVITY, activity);
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_TIME, time);
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_ACTION, "");
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_ACTION_NAME, "");
        contentValues[i].put(SituationContract.SituationEntry.COLUMN_CHECKED, "0");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(SituationListActivity.this, SituationContract.SituationEntry.CONTENT_URI,
                SituationContract.SituationEntry.SITUATION_PROJECTION,
                null, null, SituationContract.SituationEntry.COLUMN_ID + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        situationListAdapter.swapCursor(data);
        cursor = data;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        situationListAdapter.swapCursor(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, null, SituationListActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
                startActivity(new Intent(SituationListActivity.this, AddSituationActivity.class));
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


}
