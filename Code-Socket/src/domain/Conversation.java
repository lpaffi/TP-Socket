package domain;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Conversation {

    private List<Socket> members = new ArrayList<Socket>();

    private List<Message> history;

    public Conversation(List<Socket> recipients, List<Message> history) {
        this.members = recipients;
        this.history = history;
    }

    public Conversation() {
    }

    public List<Socket> getMembers() {
        return members;
    }

    public void setMembers(List<Socket> members) {
        this.members = members;
    }

    public void addMember(Socket member) {
        this.members.add(member);
    }

    public void removeMember(InetAddress member) {
        this.members.remove(member);
    }

    public List<Message> getHistory() {
        return history;
    }

    public void setHistory(List<Message> history) {
        this.history = history;
    }

    public void addMessage(Message message) {
        this.history.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(members, that.members) &&
                Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, history);
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "members=" + members +
                '}';
    }
}
