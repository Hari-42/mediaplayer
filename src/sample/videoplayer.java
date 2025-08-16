package src.sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class videoplayer extends Application {

    @Override
    public void start(Stage stage) {
        // path to a video in a string
        String videoPath = "insert your own path";

        // Media und Player
        Media media = new Media(new File(videoPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // buttons for controls
        Button playBtn = new Button("▶ Play");
        playBtn.setOnAction(e -> mediaPlayer.play());

        Button pauseBtn = new Button("⏸ Pause");
        pauseBtn.setOnAction(e -> mediaPlayer.pause());

        Button stopBtn = new Button("⏹ Stop");
        stopBtn.setOnAction(e -> mediaPlayer.stop());

        // Layout
        BorderPane root = new BorderPane();
        root.setCenter(mediaView);

        BorderPane controls = new BorderPane();
        controls.setLeft(playBtn);
        controls.setCenter(pauseBtn);
        controls.setRight(stopBtn);

        root.setBottom(controls);

        // Scene
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Simple JavaFX Video Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
