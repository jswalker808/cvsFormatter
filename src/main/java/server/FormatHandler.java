package server;

import Utils.ReaderWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.FormatResult;
import result.FormatRequest;
import service.FormatService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FormatHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {

        boolean successful = false;

        try {

            if (httpExchange.getRequestMethod().equals("POST")) {

                String requestBody = getRequest(httpExchange);
                FormatRequest request = new Gson().fromJson(requestBody, FormatRequest.class);

                FormatService service = new FormatService();


                String json = new GsonBuilder().setPrettyPrinting().create().toJson("result");

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

    private String getRequest(HttpExchange httpExchange) throws IOException {
        InputStream stream = httpExchange.getRequestBody();
        return ReaderWriter.readString(stream);
    }

    private void sendResponse(HttpExchange httpExchange, String json, int statusCode) throws IOException {
        httpExchange.sendResponseHeaders(statusCode, 0);
        httpExchange.getResponseBody().close();
    }
}
