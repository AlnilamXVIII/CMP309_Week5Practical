package com.example.cmp309_week5practical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Global variables, accessible to all methods
    //These are variables that reference the SensorManager and Sensor classes respectively
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declare reference to SensorManager class; declare SensorManager object
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //Get desired sensor from the SensorManager object via the reference
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Maps Event Handler
        //Instantiate button object called mapsButton that references the mapsButton id in the layout
        final Button mapsButton = findViewById(R.id.mapsButton);
        //Create an onClick listener, listens for the click to call startMapsActivity()
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call startBrowserActivity(); after clicking the button, this is why we need the event handler
                startMapsActivity();
            }
        });
    }

    private void startMapsActivity(){
        //Create explicit intent from this activity to MapsActivity
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Make sure not null before registering listener
        if(accelerometer!=null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("No accelerometer on device").setTitle("Sensor unavailable");

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //Cast TextView ID from MainActivity.xml, to the TextView object
            ((TextView)findViewById(R.id.textViewX)).setText(""+sensorEvent.values[0]);
            ((TextView)findViewById(R.id.textViewY)).setText(""+sensorEvent.values[1]);
            ((TextView)findViewById(R.id.textViewZ)).setText(""+sensorEvent.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}