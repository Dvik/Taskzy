package dvik.com.taskzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import dvik.com.taskzy.data.SituationActionPairModel;
import dvik.com.taskzy.adapter.SituationListAdapter;
import dvik.com.taskzy.data.SituationModel;

public class SituationListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<SituationModel> situationList;
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

        loadDefaultDataInList();
        situationListAdapter = new SituationListAdapter(situationList, SituationListActivity.this);
        recyclerView.setAdapter(situationListAdapter);



    }

    private void init()
    {
        recyclerView = (RecyclerView) findViewById(R.id.content_situation_list);
        situationList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
    }

    private void loadDefaultDataInList()
    {
        situationList.add(new SituationModel("Headphones Plugged",getString(R.string.headphone_plugged),"",null,"","",""));
        situationList.add(new SituationModel("Headphones Un-Plugged",getString(R.string.headphone_unplugged),"",null,"","",""));
        situationList.add(new SituationModel("Headphones Plugged and Still",getString(R.string.headphone_plugged),"",null,getString(R.string.still),"",""));
        situationList.add(new SituationModel("Headphones Plugged and Walking",getString(R.string.headphone_plugged),"",null,getString(R.string.walking),"",""));
        situationList.add(new SituationModel("Walking","","",null,getString(R.string.walking),"",""));
        situationList.add(new SituationModel("Running on a rainy day",getString(R.string.weather_rainy),"",null,getString(R.string.running),"",""));
        situationList.add(new SituationModel("Walking on a clear day",getString(R.string.weather_clear),"",null,getString(R.string.walking),"",""));
        situationList.add(new SituationModel("In Vehicle","","",null,getString(R.string.in_vehicle),"",""));
        situationList.add(new SituationModel("In Vehicle with Headphones plugged in",getString(R.string.headphone_plugged),"",null,getString(R.string.in_vehicle),"",""));
        situationList.add(new SituationModel("Bicycling with Headphones plugged in",getString(R.string.headphone_plugged),"",null,getString(R.string.on_bicycle),"",""));
    }

}
