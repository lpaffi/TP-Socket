package domain;

import java.net.InetAddress;
import java.util.List;
import java.util.Objects;

public class Conversation {

    private List<InetAddress> recipients;

    private List<Message> history;

    public Conversation(List<InetAddress> recipients, List<Message> history) {
        this.recipients = recipients;
        this.history = history;
    }

    public List<InetAddress> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<InetAddress> recipients) {
        this.recipients = recipients;
    }

    public List<Message> getHistory() {
        return history;
    }

    public void setHistory(List<Message> history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(recipients, that.recipients) &&
                Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipients, history);
    }
}
