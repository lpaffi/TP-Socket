package Client;

import domain.Message;
import domain.SystemMessage;

import javafx.scene.control.TextArea;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    private TextArea textArea;

    public ClientReadThread(ObjectInputStream objectInputStream, TextArea textArea) {
        this.objectInputStream = objectInputStream;
        this.textArea = textArea;
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
                this.textArea.appendText(serverMessage.toString() + "\n");
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
