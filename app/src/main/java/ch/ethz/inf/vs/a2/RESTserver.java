package ch.ethz.inf.vs.a2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

import ch.ethz.inf.vs.a2.server.RESTservice;

public class RESTserver extends AppCompatActivity {
    private RESTservice restservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restserver);
        //restservice = new RESTservice();

        Button start = (Button) findViewById(R.id.start_button);
        Button stop = (Button) findViewById(R.id.stop_button);

        String ipadd = "IP: ";
        try {
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            for (int i = 1; n.hasMoreElements(); i++) {
                NetworkInterface ne = n.nextElement();
                for (InetAddress inetAddress: Collections.list(ne.getInetAddresses())) {
                    ipadd = ipadd + inetAddress;//
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        TextView port = (TextView) findViewById(R.id.Port);
        port.setText("Port: " + "8088"); //restservice.getPORT());
        TextView ip = (TextView) findViewById(R.id.IP);
        ip.setText(ipadd);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restservice == null) {
                    restservice = new RESTservice();
                }
                //restservice.startService(new Intent());
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restservice.stopSelf();
            }
        });

    }
}
