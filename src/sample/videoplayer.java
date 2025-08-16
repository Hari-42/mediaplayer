package src.sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class videoplayer extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, World!");

        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 400, 200); // Größe: 400x200
        stage.setTitle("My First JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
