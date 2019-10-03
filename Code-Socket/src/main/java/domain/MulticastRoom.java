package domain;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastRoom implements Serializable {

    private int port;

    private InetAddress ipAddress;

    private History history;

    private MulticastSocket multicastSocket;

    public MulticastRoom() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public MulticastSocket getMulticastSocket() {
        return multicastSocket;
    }

    public void setMulticastSocket(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    @Override
    public String toString() {
        return "MulticastRoom{" +
                "port=" + port +
                ", ipAddress=" + ipAddress +
                ", history=" + history +
                ", multicastSocket=" + multicastSocket +
                '}';
    }
}
