package ch.ethz.inf.vs.a2.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RESTservice extends Service {
    private int PORT = 8088;
    ServerSocket serverSocket;
    public RESTservice() {
        try {
            serverSocket = new ServerSocket(PORT);
            while(true) {
                acceptRequest();
            }

            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void acceptRequest() throws IOException {
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while((inputLine = in.readLine()) != null) {
            parseArgs(inputLine);
        }
        String response = generateReponse("", "", 0, "");
        out.write(response);
    }
    
    private String parseArgs(String inputLine) {
        String[] s = inputLine.split(" ");
        if (s[0] == "GET" && s[2] == "HTTP/1.1") {
            String path = s[1];            
        }
            
        return null;
    }
    
    private String generateReponse(String path, String host, int port, String connection) {
        String end = "\r\n";
        String response = "GET " + path + " HTTP/1.1" + end;
        response = response + "Host: " + host + ":" + Integer.toString(port) + end;
        response = response + "Accept: text/html" + end;
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
        return START_STICKY;
    }




}
