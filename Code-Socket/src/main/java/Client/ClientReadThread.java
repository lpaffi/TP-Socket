package Client;

import domain.Message;
import domain.MulticastRoom;
import domain.SystemMessage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    private MulticastSocket multicastSocket;

    public ClientReadThread(MulticastSocket multicastSocket, ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
        this.multicastSocket = multicastSocket;
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public void run() {
        System.out.println("Running Client Read Thread");

        byte[] buf = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);

        try {
            MulticastRoom multicastRoom = (MulticastRoom) objectInputStream.readObject();
            multicastRoom.getHistory().getMessageList().forEach(message -> System.out.println(message.toString()));
            multicastSocket = new MulticastSocket(multicastRoom.getPort());
            multicastSocket.joinGroup(multicastRoom.getIpAddress());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                multicastSocket.receive(recv);
                Message serverMessage = (Message) deserialize(recv.getData()) ;
                if (serverMessage.getContent().equals(SystemMessage.DISCONNECTED.toString())) {
                    multicastSocket.leaveGroup(multicastSocket.getInetAddress());
                    this.stop();
                }
                System.out.println(serverMessage.toString());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
