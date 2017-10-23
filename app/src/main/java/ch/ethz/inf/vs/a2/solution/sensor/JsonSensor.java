package ch.ethz.inf.vs.a2.solution.sensor;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;

/**
 * Created by ruben on 22.10.17.
 */

public class JsonSensor extends AbstractSensor {
    public String executeRequest() throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature").openConnection();
        conn.setRequestProperty("Accept","application/json");
        conn.setRequestProperty("Connection","close");
        InputStream in = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result = "";
        String res;
        while((res = reader.readLine()) != null)
            result = result + res;

        Log.d("DEBUG",result);
        return result;
    }

    public double parseResponse(String response) {
        String result = "";
        JSONObject  jobj = null;
        try {
            jobj = new JSONObject(response);
            result = jobj.getString("value");

        }
        catch(Exception e) {

        }

        return Double.parseDouble(result);
    }
}
