package Client;

import domain.Message;
import domain.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;

public class ClientApplication extends Application {

    private ArrayList<Thread> threads;

    private final String SERVER_ADDRESS = "Luccas-MacBook-Air.local";
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
        threads = new ArrayList<Thread>();
        primaryStage.setTitle("JavaFX Chat Client");
        primaryStage.setScene(makeInitScene(primaryStage));
        primaryStage.show();
    }

    public Scene makeInitScene(Stage primaryStage) {
        /* Make the root pane and set properties */
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);

        /* Make the text fields and set properties */
        TextField nameField = new TextField();

        /* Make the labels and set properties */
        Label nameLabel = new Label("Nom d'utilisateur ");
        Label errorLabel = new Label();
        /* Make the button and its handler */
        Button submitClientInfoButton = new Button("Entrer");
        submitClientInfoButton.setOnAction(event -> {
            String clientName = nameField.getText();
            User client = new User();
            client.setName(clientName);

            /* Change the scene of the primaryStage */
            primaryStage.close();
            primaryStage.setScene(makeChatUI(client));
            primaryStage.show();
        });

        /*
         * Add the components to the root pane arguments are (Node, Column
         * Number, Row Number)
         */
        rootPane.add(nameField, 1, 0);
        rootPane.add(nameLabel, 0, 0);
        rootPane.add(submitClientInfoButton, 0, 3, 2, 1);
        rootPane.add(errorLabel, 0, 4);
        /* Make the Scene and return it */
        return new Scene(rootPane, 400, 400);
    }

    public Scene makeChatUI(User client) {
        /* Make the root pane and set properties */
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));

        TextField chatTextField = new TextField(); // Area for user input
        TextArea chatArea = new TextArea(); // Area for exchanged messages

        /* Add the components to the root pane */
        rootPane.add(chatTextField, 0, 9, 5, 1);
        rootPane.add(chatArea, 0, 0, 5, 5);

        // Initialize client thread
        System.out.println("Initializing Client Thread");
        TestClient clientThread = new TestClient(SERVER_ADDRESS, SERVER_PORT, client, chatArea);
        clientThread.start();

        chatTextField.setOnAction(event -> {
            //Create and send message
            String content = chatTextField.getText();
            Message message = new Message(client.getName(), content, new Date());
            clientThread.sendMessage(message);
            chatTextField.clear();
        });

        /* Make and return the scene */
        return new Scene(rootPane, 400, 400);
    }


}
