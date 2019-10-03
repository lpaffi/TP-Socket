package server;

import domain.History;
import domain.Message;
import domain.MulticastRoom;
import domain.SystemMessage;
import util.ByteSerializationUtil;

import java.io.IOException;
import java.net.*;

public class DaemonThread extends Thread {

    private static int MULTICAST_SERVER_PORT = 6789;

    private static String MULTICAST_SERVER_ADDRESS = "228.5.6.7";

    private History history;

    public DaemonThread() {
        this.history = new History();
    }

    public void run() {
        MulticastSocket multicastSocket = null;
        try {
            multicastSocket = new MulticastSocket(MULTICAST_SERVER_PORT);
            multicastSocket.joinGroup(new InetSocketAddress(InetAddress.getByName(MULTICAST_SERVER_ADDRESS), MULTICAST_SERVER_PORT), NetworkInterface.getByName("p2p0"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buf = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);

        while (true) {
            try {
                multicastSocket.receive(recv);
                Message incomingMessage = (Message) ByteSerializationUtil.deserialize(recv.getData());
                if (incomingMessage.getContent().equals(SystemMessage.DISCONNECTED.toString())) {
                    multicastSocket.leaveGroup(multicastSocket.getInetAddress());
                    this.stop();
                }
                System.out.println("Daemon read message: "+incomingMessage.toString());
                history.addMessage(incomingMessage);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
