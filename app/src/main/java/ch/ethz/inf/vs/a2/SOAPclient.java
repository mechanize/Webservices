package ch.ethz.inf.vs.a2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import ch.ethz.inf.vs.a2.sensor.SensorListener;
import ch.ethz.inf.vs.a2.solution.sensor.RawHttpSensor;
import ch.ethz.inf.vs.a2.solution.sensor.SoapSensor;
import ch.ethz.inf.vs.a2.solution.sensor.TextSensor;
import ch.ethz.inf.vs.a2.solution.sensor.XmlSensor;

/**
 * Created by G on 22.10.2017.
 **/

public class SOAPclient extends AppCompatActivity implements SensorListener {

    TextView txt;
    TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soapclient);


        //first sensor
        XmlSensor xmlSens = new XmlSensor();
        xmlSens.registerListener(new SOAPclient.XmlListener());
        txt = (TextView) findViewById(R.id.textView5);
        xmlSens.getTemperature();

        //second sensor
        SoapSensor soapSens = new SoapSensor();
        soapSens.registerListener(new SOAPclient.soapListener());
        txt2 = (TextView) findViewById(R.id.textView6);
        soapSens.getTemperature();
    }

    public void onReceiveSensorValue(double value) {
    }

    public void onReceiveMessage(String message) {
    }

    class XmlListener implements SensorListener {
        public void onReceiveSensorValue(double value) {
            txt.setText("Temperature from XmlSensor: " + String.valueOf(value));
        }

        public void onReceiveMessage(String message) {

        }
    }

    class soapListener implements SensorListener {
        public void onReceiveSensorValue(double value) {
            Log.d("soaplistener","entered");
            txt2.setText("Temperature from SoapSensor: " + String.valueOf(value));
        }

        public void onReceiveMessage(String message) {

        }

    }



}
