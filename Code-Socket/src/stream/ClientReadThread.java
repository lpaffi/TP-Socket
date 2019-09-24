package stream;

import domain.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    public ClientReadThread(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void run() {
        System.out.println("Running Client Read Thread");
        while (true) {
            try {
                Message clientMessage = (Message) objectInputStream.readObject();
                System.out.println(clientMessage.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
