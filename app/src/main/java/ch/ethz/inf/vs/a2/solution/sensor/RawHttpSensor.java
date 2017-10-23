package ch.ethz.inf.vs.a2.solution.sensor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;
import ch.ethz.inf.vs.a2.solution.http.HttpRawRequestImpl;

/**
 * Created by ruben on 22.10.17.
 */

public class RawHttpSensor extends AbstractSensor {

    public String executeRequest() throws Exception {
        int port = 8081;
        String host = "vslab.inf.ethz.ch";
        String path = "/sunspots/Spot1/sensors/temperature";

        //generate Http request
        HttpRawRequestImpl rqImpl = new HttpRawRequestImpl();
        String request = rqImpl.generateRequest(host,port,path);

        //open connection and send
        Socket so = new Socket(host,port);
        PrintWriter out = new PrintWriter(so.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(so.getInputStream()));
        out.print(request);
        out.flush();
        String result = "";
        String res;
        while((res = in.readLine()) != null)
            result = result + res + '\n';

        //end conncection
        in.close();
        out.close();
        so.close();

        return result;
    }

    public double parseResponse(String response) {
        String searchS = "<span class=\"getterValue\">";
        int indexStart = response.indexOf(searchS);
        String subS = response.substring(indexStart + searchS.length());
        String tempS = "";

        //exctract temp. string from the cut response
        int ind = 0;
        while(subS.charAt(ind) != '<') {
            tempS = tempS + subS.charAt(ind);
            ind = ind + 1;
        }

        return Double.parseDouble(tempS);
    }
}
