package ch.ethz.inf.vs.a2.server;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RESTservice extends Service {
    private int PORT = 8088;
    ServerSocket serverSocket;

    public void acceptRequest() throws IOException {
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        List<String> inputLines = new ArrayList<>();
        while ((inputLine = in.readLine()) != null) {
            inputLines.add(inputLine);
        }
        parseArgs(inputLines);
        String response = generateReponse("", "", "", 0, "");
        out.write(response);
    }

    private String parseArgs(List<String> inputLines) {
        String path = "";
        for (String line: inputLines) {
            String[] args = line.split(" ");
            if (args[0] == "GET" && args[2] == "HTTP/1.1") {
                path = args[1];
            }
        }
        switch(path) {
            case("/"):
                break;
            case("/accel"):
                break;
            case("/light"):
                break;
            default:
                break;
        }


        return null;
    }

    private String generateReponse(String path, String host, String answ, int port, String connection) {
        String end = "\r\n";
        String response = "GET " + path + " HTTP/1.1" + end;
        response = response + "Host: " + host + ":" + Integer.toString(port) + end;
        response = response + "Accept: text/html" + answ + end;
        response = response + "Connection: " + connection + " " + end + end;
        return response;
    }

    public int getPORT() {
        return PORT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                acceptRequest();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }
}

