package Client;


import domain.Message;
import domain.SystemMessage;
import domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.Date;

public class ClientWriteThread extends Thread {

    ObjectOutputStream objectOutputStream;

    User user;

    public ClientWriteThread(ObjectOutputStream objectOutputStream, User user) {
        this.objectOutputStream = objectOutputStream;
        this.user = user;
    }

    public void sendMessage(Message message) {
        System.out.println("sendMessage in ClientWriteThread");
        try {
            objectOutputStream.writeObject(message);
            if (message.getContent().equals(SystemMessage.QUIT.toString())) {
                this.stop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            Message message = new Message(user.getName(), line, new Date());
            sendMessage(message);
        }
    }
}
