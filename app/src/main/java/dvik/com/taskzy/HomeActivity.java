package dvik.com.taskzy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;

import dvik.com.taskzy.adapter.StaggeredAdapter;
import dvik.com.taskzy.data.SituationContract;
import dvik.com.taskzy.utils.Constants;
import dvik.com.taskzy.utils.Utils;

public class HomeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    StaggeredAdapter staggeredAdapter;
    GoogleApiClient mGoogleApiClient;
    Cursor cursor;
    CardView emptyLayout;
    CoordinatorLayout coordinatorLayout;

    private static final int CURSOR_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        if (Utils.isGooglePlayServicesAvailable(HomeActivity.this)) {
            mGoogleApiClient = TaskApplication.getGoogleApiHelper().getGoogleApiClient();
        } else {
            Snackbar.make(coordinatorLayout, R.string.warning_no_play_services,Snackbar.LENGTH_LONG).show();
        }


        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        staggeredAdapter = new StaggeredAdapter(mGoogleApiClient, cursor, HomeActivity.this);
        recyclerView.setAdapter(staggeredAdapter);

    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.content_home);
        emptyLayout = (CardView) findViewById(R.id.empty_layout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_home);
        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, HomeActivity.this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent i = new Intent(HomeActivity.this, CreatePairActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(HomeActivity.this, SituationContract.SituationEntry.CONTENT_URI,
                SituationContract.SituationEntry.SITUATION_PROJECTION,
                SituationContract.SituationEntry.COLUMN_ACTION + "!= ?", new String[]{""},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        staggeredAdapter.swapCursor(data);
        cursor = data;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        staggeredAdapter.swapCursor(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, null, HomeActivity.this);

        Cursor c = getContentResolver().query(SituationContract.SituationEntry.CONTENT_URI, new String[]{SituationContract.SituationEntry.COLUMN_ID},
                SituationContract.SituationEntry.COLUMN_ACTION + "!= ?", new String[]{""}, null);
        if (c != null) {
            if (c.getCount() != 0) {
                emptyLayout.setVisibility(View.GONE);
            }
            c.close();
        }
        if (!Utils.hasLocationPermission(this)) {
            Utils.requestLocationPermission(this, Constants.LOCATION_REQUEST_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.LOCATION_REQUEST_CODE) {

            }
        }
    }
}
