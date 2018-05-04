package lab08.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread {
    private Socket socket;
    private final GameServer server;
    private GuessingGame game;
    private boolean running = true;
    private int wait = 20; //seconds

    // Create the constructor that receives a reference to the server and to the client socket
    ClientThread(GameServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        while(running) {
            System.out.println("Attempt to get socket input stream!");
            BufferedReader in = null; //client -> server stream
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Attempt to read the command and execute it!");
            String request = "";
            String response = null;
            try {
                if (in != null) {
                    if (in.ready()) {
                        System.out.println("Socket ready to read from!");
                    }

                    try {
                        while ((request = in.readLine()) == null && wait > 0) {
                            try {
                                wait -= 1;
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (SocketException e) {
                        System.out.println(e);
                        return;
                    }
                    wait = 20;

                    System.out.println("Message " + request + " was read!");

                    response = execute(request);
                } else {
                    System.out.println("Couldn't get client request!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Attempt to send confirmation message to the client!");
            PrintWriter out; //server -> client stream
            try {
                out = new PrintWriter(socket.getOutputStream());
                if (response != null) {
                    System.out.println("Sent server response to client: \"" + response.trim() + "\" !");
                    out.println(response);
                    out.flush();
                } else {
                    System.out.println("Couldn't send response to the client!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (request != null && request.equals("stop")) {
                try {
                    server.stop();
                    // return; // you force it to finish from a single client
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(request == null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String execute(String request) {
        if(request == null) {
            return "Disconnected from the server!";
        }

        System.out.println("Server received the request " + request + "!");

        String command = request.split(" ")[0];
        String message;

        switch(command) {
            case "create":
                this.game = new GuessingGame(this.socket.getLocalAddress().getHostAddress(), this);
                message = this.game.getWelcomeMessage();
                break;

            case "submit":
                if (this.game != null)
                    message = this.game.guess(Integer.valueOf(request.split(" ")[1]));
                else
                    message = "You need to create a game first!";
                break;

            case "quit":
                if (this.game != null) {
                    message = this.game.quit();

                    this.game.writeFile();
                    this.game.uploadFile();
                    this.game.deleteFile();

                    this.game = null;
                    this.running = false;
                }
                else
                    message = "You need to create a game first!";
                break;

            case "stop":
                message = "The server will be shut down after all games will finish!";
                break;

            default:
                message = "Unrecognized command!";
                break;
        }

        if ( this.game != null && this.game.getAttempts() == 0) {
            message += " " + this.game.quit();
            System.out.println("GuessingGame was lost!");

            this.game.writeFile();
            this.game.uploadFile();
            this.game.deleteFile();

            this.game = null;
        }

        return message;
    }
}