package ch.ethz.inf.vs.a2.solution.http;

import ch.ethz.inf.vs.a2.http.HttpRawRequest;

/**
 * Created by ruben on 21.10.17.
 */

public class HttpRawRequestImpl implements HttpRawRequest {
    public String generateRequest(String host, int port, String path) {
        String end = "\r\n";
        String request = "GET " + path + " HTTP/1.1" + end;
        request = request + "Host: "+ host + ":" + port + end;
        request = request + "Accept: text/html" + end;
        request = request + "Connection: close" + end + end;
        return request;
    }
}
