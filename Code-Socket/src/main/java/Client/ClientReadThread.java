package Client;

import domain.Message;
import domain.MulticastRoom;
import domain.SystemMessage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClientReadThread extends Thread {

    private ObjectInputStream objectInputStream;

    private MulticastRoom multicastRoom;

    public ClientReadThread(MulticastRoom multicastRoom, ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
        this.multicastRoom = multicastRoom;
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
            MulticastRoom receivedMulticastRoom = (MulticastRoom) objectInputStream.readObject();
            multicastRoom.setHistory(receivedMulticastRoom.getHistory());
            multicastRoom.setPort(receivedMulticastRoom.getPort());
            multicastRoom.setIpAddress(receivedMulticastRoom.getIpAddress());

            MulticastSocket multicastSocket = new MulticastSocket(multicastRoom.getPort());
            multicastRoom.setMulticastSocket(multicastSocket);

            System.out.println("multicast room = " +multicastRoom.toString());
            multicastRoom.getHistory().getMessageList().forEach(message -> System.out.println(message.toString()));
            multicastSocket.joinGroup(multicastRoom.getIpAddress());
            System.out.println("multicast socket port 2 = "+multicastSocket.getPort());
            System.out.println(multicastRoom.getMulticastSocket().getPort());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            MulticastSocket multicastSocket = multicastRoom.getMulticastSocket();
            System.out.println("test");
            try {
                System.out.println("socket port = "+multicastSocket.getPort());
                multicastSocket.receive(recv);
                System.out.println("socket port = "+multicastSocket.getPort());
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
