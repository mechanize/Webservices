package ch.ethz.inf.vs.a2.solution.sensor;

import android.util.Log;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by G on 22.10.2017.
 **/

public class SoapSensor extends AbstractSensor {
    private static final String NAMESPACE = "http://webservices.vslecture.vs.inf.ethz.ch/";
    private static final String URL ="http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice?wsdl";
    private static final String SOAP_ACTION = "http://webservices.vslecture.vs.inf.ethz.ch/getSpot";
    private static final String METHOD_NAME = "getSpot";

    public String executeRequest() throws Exception {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("id","Spot4");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.call(SOAP_ACTION, envelope);
        SoapObject resultsRequestSOAP = (SoapObject) envelope.getResponse();//bodyIn;
        //Log.d("Result","GotToResult");
        String Result = resultsRequestSOAP.getPropertyAsString("temperature");
        //Log.d("Resultsoap",Result);
        return Result;
    }

    public double parseResponse(String response){
        double res = Double.parseDouble(response);
        return res;
    }


}
