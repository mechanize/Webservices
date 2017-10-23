package ch.ethz.inf.vs.a2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.ethz.inf.vs.a2.server.RESTservice;

public class RESTserver extends AppCompatActivity {
    private RESTservice restservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restserver);
        restservice = new RESTservice();

        Button start = (Button) findViewById(R.id.start_button);
        Button stop = (Button) findViewById(R.id.stop_button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restservice.startService(new Intent());
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
