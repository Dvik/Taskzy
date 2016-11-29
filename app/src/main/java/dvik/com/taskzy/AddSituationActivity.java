package dvik.com.taskzy;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import dvik.com.taskzy.data.SituationContract;

public class AddSituationActivity extends AppCompatActivity {


    ImageButton cancelHeadphone, cancelWeather, cancelLocation, cancelActivity, cancelTime;
    CardView headPhoneCard, weatherCard, locationCard, activityCard, timeCard;
    TextView tvHeadphone, tvWeather, tvLocation, tvActivity, tvTime;
    EditText nameEdt;
    String nameText, headPhoneText, weatherText, latitudeText, longitudeText, activityText, timeText;
    Long timeLongText;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_situation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        cancelOnButtonPress();
        showDialogBox();
    }

    private void init() {
        cancelHeadphone = (ImageButton) findViewById(R.id.cancel_headphone);
        cancelWeather = (ImageButton) findViewById(R.id.cancel_weather);
        cancelLocation = (ImageButton) findViewById(R.id.cancel_location);
        cancelActivity = (ImageButton) findViewById(R.id.cancel_user_activity);
        cancelTime = (ImageButton) findViewById(R.id.cancel_time);

        headPhoneCard = (CardView) findViewById(R.id.headphone_card);
        weatherCard = (CardView) findViewById(R.id.weather_card);
        locationCard = (CardView) findViewById(R.id.location_card);
        activityCard = (CardView) findViewById(R.id.user_activity_card);
        timeCard = (CardView) findViewById(R.id.time_date_card);

        tvHeadphone = (TextView) findViewById(R.id.tv_headphone);
        tvWeather = (TextView) findViewById(R.id.tv_weather);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvActivity = (TextView) findViewById(R.id.tv_activity);
        tvTime = (TextView) findViewById(R.id.tv_time);

        nameEdt = (EditText) findViewById(R.id.name_edt);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        nameText = headPhoneText = weatherText = latitudeText = longitudeText = activityText = timeText = "";
        timeLongText = 0L;
    }

    private void cancelOnButtonPress() {
        cancelHeadphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headPhoneCard.setVisibility(View.GONE);
            }
        });

        cancelWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherCard.setVisibility(View.GONE);
            }
        });

        cancelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationCard.setVisibility(View.GONE);
            }
        });

        cancelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCard.setVisibility(View.GONE);
            }
        });

        cancelTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeCard.setVisibility(View.GONE);
            }
        });

    }

    private void showDialogBox() {
        final String[] headPhoneStates = getResources().getStringArray(R.array.headphone_state_array);
        final String[] weatherStates = getResources().getStringArray(R.array.weather_array);
        final String[] activityStates = getResources().getStringArray(R.array.activity_array);

        headPhoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddSituationActivity.this);

                builder.setTitle("Select Headphone State")
                        .setItems(R.array.headphone_state_array, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                headPhoneText = headPhoneStates[i];
                                tvHeadphone.setText(headPhoneText);
                            }
                        });

                builder.show();
            }
        });


        weatherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddSituationActivity.this);

                builder.setTitle("Select Weather State")
                        .setItems(R.array.weather_array, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                weatherText = weatherStates[i];
                                tvWeather.setText(weatherText);
                            }
                        });

                builder.show();
            }
        });

        activityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddSituationActivity.this);

                builder.setTitle("Select Activity State")
                        .setItems(R.array.activity_array, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                activityText = activityStates[i];
                                tvActivity.setText(activityText);
                            }
                        });

                builder.show();
            }
        });

        locationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddSituationActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_location, null);

                final EditText lat = (EditText) dialogView.findViewById(R.id.latitude);
                final EditText lon = (EditText) dialogView.findViewById(R.id.longitude);

                builder.setTitle("Choose Location")
                        .setView(dialogView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                latitudeText = lat.getText().toString();
                                longitudeText = lon.getText().toString();
                                if (TextUtils.isEmpty(latitudeText) || TextUtils.isEmpty(longitudeText)) {
                                    Snackbar.make(coordinatorLayout, "Please fill in both LATITUDE and LONGITUDE", Snackbar.LENGTH_SHORT)
                                            .show();
                                } else {
                                    tvLocation.setText(latitudeText + "," + longitudeText);
                                    dialogInterface.cancel();
                                }
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                builder.show();

            }
        });


        timeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .setMinDate(new Date())
                        .build()
                        .show();
            }
        });


    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            try {
                timeText = simpleDateFormat.format(date);
                tvTime.setText(timeText);
                timeLongText = date.getTime();

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void onDateTimeCancel() {
        }
    };

    private void saveData() {
        String savedTime;
        nameText = nameEdt.getText().toString();

        if (TextUtils.isEmpty(nameText)) {
            Snackbar.make(coordinatorLayout, R.string.no_name_warning, Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (timeLongText == 0L) {
            savedTime = "";
        } else {
            savedTime = String.valueOf(timeLongText);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(SituationContract.SituationEntry.COLUMN_NAME,nameText);
        contentValues.put(SituationContract.SituationEntry.COLUMN_HEADPHONE_STATE,headPhoneText);
        contentValues.put(SituationContract.SituationEntry.COLUMN_WEATHER_STATE,weatherText);
        contentValues.put(SituationContract.SituationEntry.COLUMN_LATITUDE,latitudeText);
        contentValues.put(SituationContract.SituationEntry.COLUMN_LONGITUDE,longitudeText);
        contentValues.put(SituationContract.SituationEntry.COLUMN_ACTIVITY,activityText);
        contentValues.put(SituationContract.SituationEntry.COLUMN_TIME,savedTime);
        contentValues.put(SituationContract.SituationEntry.COLUMN_ACTION,"");
        contentValues.put(SituationContract.SituationEntry.COLUMN_ACTION_NAME,"");


        getContentResolver().insert(SituationContract.SituationEntry.CONTENT_URI,
                contentValues);

        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_create) {
            saveData();
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }
}
