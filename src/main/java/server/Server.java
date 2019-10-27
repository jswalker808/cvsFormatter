package server;

import com.sun.net.httpserver.HttpServer;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber) {

        System.out.println("Initalizing HTTP Server");

        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/format");

        server.start();

        System.out.println("Server running on port " + portNumber);
    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: programname <portnumber>");
        } else {
            String portNumber = args[0];
            new Server().run(portNumber);
        }

    }
}
