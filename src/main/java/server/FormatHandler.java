package server;

import Utils.ReaderWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.FormatResult;
import result.FormatRequest;
import service.FormatService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class FormatHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {

        boolean successful = false;

        try {

            if (httpExchange.getRequestMethod().equals("POST")) {

                FormatRequest request = getRequest(httpExchange);

                FormatService service = new FormatService();
                FormatResult result = service.format(request.getCsvString());

                String json = new GsonBuilder().setPrettyPrinting().create().toJson(result);

                sendResponse(httpExchange, json, HttpURLConnection.HTTP_OK);

                successful = true;
            }

            if (!successful) {
                sendResponse(httpExchange, "", HttpURLConnection.HTTP_BAD_REQUEST);
            }

        } catch (IOException ex) {
            sendResponse(httpExchange, "", HttpURLConnection.HTTP_INTERNAL_ERROR);
            ex.printStackTrace();
        }

    }

    private FormatRequest getRequest(HttpExchange httpExchange) throws IOException {
        InputStream stream = httpExchange.getRequestBody();
        String json = ReaderWriter.readString(stream);
        return new Gson().fromJson(json, FormatRequest.class);
    }

    private void sendResponse(HttpExchange httpExchange, String json, int statusCode) throws IOException {
        List<String> contentTypes = new ArrayList<String>();
        contentTypes.add("application/json");

        Headers headers = httpExchange.getResponseHeaders();
        headers.put("Content-Type", contentTypes);
        httpExchange.sendResponseHeaders(statusCode, 0);

        if (statusCode == HttpURLConnection.HTTP_OK) {
            OutputStream responseBody = httpExchange.getResponseBody();
            ReaderWriter.writeString(json, responseBody);
        }

        httpExchange.getResponseBody().close();
    }
}
