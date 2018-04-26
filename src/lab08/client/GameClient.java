package lab08.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT = 8100;

    private Socket socket = null;

    public static void main(String[] args) {
        GameClient client = new GameClient();
        while (true) {
            String request = client.readFromKeyboard();
            client.sendRequestToServer(request);

            if (request.equalsIgnoreCase("exit"))
                break;
        }
    }

    // Implement the sendRequestToServer method
    private void sendRequestToServer(String request){
        try {
            if (socket == null || socket.isClosed() || !socket.isConnected()) {
                socket = new Socket(SERVER_ADDRESS, PORT);
            }

            //Send the message to the server
            PrintWriter outputPW = new PrintWriter(socket.getOutputStream());
            outputPW.println(request + "\r\n");
            outputPW.flush();

            System.out.println("Message " + request + " sent to the server!");

            BufferedReader inputBF = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = inputBF.readLine();
            System.out.println("Message from server: " + message);
        } catch (IOException e) {
            System.out.println(e);
            try {
                System.out.println("Trying to reconnect...");
                socket = new Socket(SERVER_ADDRESS, PORT);
                System.out.println("Successfully reconnected!");
            } catch (IOException e1) {
                System.out.println(e1);
            }
        }
        System.out.println();
    }

    private String readFromKeyboard() {
        System.out.println("Send your request to server!");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}