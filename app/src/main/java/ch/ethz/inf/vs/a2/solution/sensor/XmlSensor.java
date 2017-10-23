package ch.ethz.inf.vs.a2.solution.sensor;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;

/**
 * Created by G on 22.10.2017.
 **/

public class XmlSensor extends AbstractSensor {
    String XMLSRequest = "<?xml version=\"1.0\"" +
            " encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"+
            "    <S:Header/>\n"+
            "    <S:Body>\n"+
            "        <ns2:getSpot xmlns:ns2=\"http://webservices.vslecture.vs.inf.ethz.ch/\">\n"+
            "            <id>Spot3</id>\n"+
            "        </ns2:getSpot>\n"+
            "    </S:Body>\n"+
            "</S:Envelope>";
    public String executeRequest() throws Exception {
        //Log.d("XMLEntered","XMLEntered");
    String url = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice?wsdl";
    URL obj = new URL(url);
    URLConnection con = (HttpURLConnection) obj.openConnection();

    //add request header
    //con.setRequestMethod("POST");
    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    con.setRequestProperty("Content-Length", "288");
    //con.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
    con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
    //con.setRequestProperty("SOAPAction", "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice?wsdl");
    // Send post request
    con.setDoOutput(true);
    OutputStream os = con.getOutputStream();
    /*PrintWriter out = new PrintWriter(con.getOutputStream());
        out.print(XMLSRequest);
        out.flush();*/
    os.write(XMLSRequest.getBytes("utf-8"));
    InputStream ins = con.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        String result = "";
        String res;
        while((res = in.readLine()) != null)
            result = result + res + '\n';

        os.close();
        //out.close();
        ins.close();
        //Log.d("ResultXML",result);
        return result;
    }
    public double parseResponse(String response){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(response));
            int eventType = xpp.getEventType();
            String resp = "";
            double resu;
            boolean fl = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    //Log.d("TAGS",xpp.getName());
                    if("temperature".equals(xpp.getName())) {
                        //Log.d("temperature","has been entered");
                        fl = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && fl) {
                    resp = xpp.getText();
                    //Log.d("RESUVALUE",resp);
                    fl = false;
                }
                eventType = xpp.next();
            }
            resu = Double.parseDouble(resp);
            return resu;
        }
        catch (XmlPullParserException|IOException e){
            return 0d;
        }
        }
    }

