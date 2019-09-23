package domain;

import java.net.InetAddress;
import java.util.Date;
import java.util.Objects;

public class Message {

    private InetAddress author;

    private String content;

    private Date date;

    public Message(InetAddress author, String content, Date date) {
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public InetAddress getAuthor() {
        return author;
    }

    public void setAuthor(InetAddress author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(author, message.author) &&
                Objects.equals(content, message.content) &&
                Objects.equals(date, message.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, content, date);
    }
}
