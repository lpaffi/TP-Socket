package domain;

public enum SystemMessage {

    QUIT("quitter"), JOIN("rejoindre"), DISCONNECTED("déconnecter");

    public final String message;

    private SystemMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return message;
    }

}
