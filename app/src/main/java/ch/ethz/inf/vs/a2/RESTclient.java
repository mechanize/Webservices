package ch.ethz.inf.vs.a2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ch.ethz.inf.vs.a2.sensor.SensorListener;
import ch.ethz.inf.vs.a2.solution.sensor.JsonSensor;
import ch.ethz.inf.vs.a2.solution.sensor.RawHttpSensor;
import ch.ethz.inf.vs.a2.solution.sensor.TextSensor;


public class RESTclient extends AppCompatActivity implements SensorListener{

    TextView txt;
    TextView txt2;
    TextView txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restclient);


        //first sensor
        RawHttpSensor httpSens = new RawHttpSensor();
        httpSens.registerListener(new rawListener());
        txt = (TextView) findViewById(R.id.textView);
        httpSens.getTemperature();

        //second sensor
        TextSensor textSens = new TextSensor();
        textSens.registerListener(new textListener());
        txt2 = (TextView) findViewById(R.id.textView2);
        textSens.getTemperature();

        //third sensor
        JsonSensor jsonSens = new JsonSensor();
        jsonSens.registerListener(this);
        txt3 = (TextView) findViewById(R.id.textView3);
        jsonSens.getTemperature();
    }

    public void onReceiveSensorValue(double value) {
        txt3.setText("Temperature from JsonSensor: " + String.valueOf(value));
    }

    public void onReceiveMessage(String message) {

    }

    class rawListener implements SensorListener {
        public void onReceiveSensorValue(double value) {
            txt.setText("Temperature from RawHttp: " + String.valueOf(value));
        }

        public void onReceiveMessage(String message) {

        }
    }

    class textListener implements SensorListener {
        public void onReceiveSensorValue(double value) {
            txt2.setText("Temperature from TextSensor: " + String.valueOf(value));
        }

        public void onReceiveMessage(String message) {

        }
    }
}
