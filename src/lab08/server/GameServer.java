package lab08.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 8100;
    private ServerSocket serverSocket;
    private boolean running = false;

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.init();

        try {
            server.waitForClients(); //... handle the exceptions!
        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("Server closed!");
    }

    // Implement the init() method: create the serverSocket and set running to true

    private void init() {
        try {
            serverSocket = new ServerSocket(PORT);
            running = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Implement the waitForClients() method: while running is true,
    // create a new socket for every incoming client and start a ClientThread to execute its request.

    private void waitForClients() throws IOException {
        System.out.println("Server running: " + running + "!");
        while(running && !serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            System.out.println();
            System.out.println("Client connected!");
            System.out.println("Client thread running!");
            (new ClientThread(this, clientSocket)).start();
        }
    }

    void stop() throws IOException {
        serverSocket.close();
        System.out.println("Socket closed!");
    }
}