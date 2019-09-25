package stream;

import domain.History;
import domain.Message;
import domain.SystemMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    public ClientReadThread(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void run() {
        System.out.println("Running Client Read Thread");

        try {
            List<Message> historique = (List<Message>) objectInputStream.readObject();
            historique.forEach(message -> System.out.println(message.toString()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Message serverMessage = (Message) objectInputStream.readObject();
                if (serverMessage.getContent().equals(SystemMessage.DISCONNECTED.toString())) {
                    this.stop();
                }
                System.out.println(serverMessage.toString());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
