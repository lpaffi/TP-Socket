package stream;

import domain.History;
import domain.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    private final String QUIT_MESSAGE = "Disconnecting";

    public ClientReadThread(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void run() {
        System.out.println("Running Client Read Thread");

        try {
            List<Message> historique = (List<Message>) objectInputStream.readObject();
            historique.forEach(message -> System.out.println(message.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
