package dvik.com.taskzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import dvik.com.taskzy.data.SituationModel;
import dvik.com.taskzy.adapter.SituationListAdapter;

public class SituationListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<SituationModel> situationModelList;
    LinearLayoutManager layoutManager;
    SituationListAdapter situationListAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SituationListActivity.this,AddSituationActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        init();

        recyclerView.setLayoutManager(layoutManager);
        situationModelList.add(new SituationModel("Walk with headphones on","Plugged","","","Walking",""));
        situationModelList.add(new SituationModel("Idle with headphones on","Plugged","","","Idle",""));

        situationListAdapter = new SituationListAdapter(situationModelList, SituationListActivity.this);
        recyclerView.setAdapter(situationListAdapter);



    }

    private void init()
    {
        recyclerView = (RecyclerView) findViewById(R.id.content_situation_list);
        situationModelList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
    }

}
