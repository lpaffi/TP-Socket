package stream;


import domain.Message;
import domain.User;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientWriteThread extends Thread {

    ObjectOutputStream objectOutputStream;

    User user;

    private static String EXIT_MESSAGE = "quit";

    public ClientWriteThread(Socket socket, User user) {
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.user = user;
    }

    public void run() {
        System.out.println("Running Client Write Thread");

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (true) {
            try {
                line = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line.equals(EXIT_MESSAGE)) break;
            Message message = new Message(user.getName(), line, new Date());
            try {
                objectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
