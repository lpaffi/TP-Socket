package Client;

import domain.Message;
import domain.SystemMessage;
import domain.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;

public class ClientApplication extends Application {

    private ArrayList<Thread> threads;

    private final String SERVER_ADDRESS = "127.0.0.1";
    private final int SERVER_PORT = 9999;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // TODO Auto-generated method stub
        threads = new ArrayList<>();
        primaryStage.setTitle("JavaFX Chat Client");
        primaryStage.setScene(makeInitScene(primaryStage));
        primaryStage.show();
    }

    public Scene makeInitScene(Stage primaryStage) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(15, 20, 10, 10));

        /* Make the text fields and set properties */
        TextField nameField = new TextField();
        /* Make the labels and set properties */
        Label nameLabel = new Label("Quel est votre nom d'utilisateur?");
        Label infoLabel = new Label("Appuyez sur Entrer pour rejoindre la conversation");
        vBox.getChildren().addAll(nameLabel, nameField, infoLabel);

        /* Add handler to textfield */
        nameField.setOnAction(event -> {
            String clientName = nameField.getText();
            User client = new User();
            client.setName(clientName);

            /* Change the scene of the primaryStage */
            primaryStage.close();
            primaryStage.setScene(makeChatUI(client, primaryStage));
            primaryStage.show();
        });
        Scene scene = new Scene(vBox, 400, 400);
        return scene;
    }

    public Scene makeChatUI(User client, Stage primaryStage) {
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(15, 20, 10, 10));

        TextArea chatArea = new TextArea(); // Area for exchanged messages
        TextField chatTextField = new TextField(); // Area for user input
        chatArea.setEditable(false);

        Button quitButton = new Button("Quitter");

        vBox.getChildren().addAll(chatArea, chatTextField, quitButton);

        // Initialize client thread
        System.out.println("Initializing Client Thread");
        EchoClient clientThread = new EchoClient(SERVER_ADDRESS, SERVER_PORT, client, chatArea);
        clientThread.start();

        chatTextField.setOnAction(event -> {
            //Create and send message
            String content = chatTextField.getText();
            Message message = new Message(client.getName(), content, new Date());
            clientThread.sendMessage(message);
            chatTextField.clear();
        });

        quitButton.setOnMouseClicked(mouseEvent -> {
            Message message = new Message(client.getName(), SystemMessage.QUIT.toString(), new Date());
            clientThread.sendMessage(message);
            primaryStage.close();
            primaryStage.setScene(makeInitScene(primaryStage));
            primaryStage.show();
        });

        /* Make and return the scene */
        Scene scene = new Scene(vBox);
        return scene;
    }


}
