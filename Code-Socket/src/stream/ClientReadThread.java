package stream;

import domain.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    public ClientReadThread(Socket socket) {
        try {
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
