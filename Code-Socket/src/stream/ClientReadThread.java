package stream;

import domain.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    private final String QUIT_MESSAGE = "Disconnecting";

    public ClientReadThread(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void run() {
        System.out.println("Running Client Read Thread");
        while (true) {
            try {
                Message serverMessage = (Message) objectInputStream.readObject();
                System.out.println(serverMessage.toString());
                if (serverMessage.getContent().equals(QUIT_MESSAGE)) {
                    System.out.println("Vous êtes deconnectées");
                    this.stop();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
