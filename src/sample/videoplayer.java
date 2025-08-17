package src.sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.File;

public class videoplayer extends Application {

    @Override
    public void start(Stage stage) {
        // path to a video in a string
        String videoPath = "C:/Users/mshha/Videos/test.mp4";

        // Media und Player
        Media media = new Media(new File(videoPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // scalable
        mediaView.setPreserveRatio(true);
        mediaView.setFitWidth(800);
        mediaView.setFitHeight(600);

        // Progress Slider
        Slider progressBar = new Slider();
        progressBar.setMin(0);
        progressBar.setMax(100);
        progressBar.setValue(0);

        progressBar.setMaxWidth(Double.MAX_VALUE);

        // update progress bar as video plays
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progressBar.isValueChanging() && mediaPlayer.getTotalDuration() != null) {
                double progress = newTime.toMillis() / mediaPlayer.getTotalDuration().toMillis() * 100.0;
                progressBar.setValue(progress);
            }
        });

        // seek video when user drags slider
        progressBar.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging && mediaPlayer.getTotalDuration() != null) {
                double seekTime = progressBar.getValue() / 100.0 * mediaPlayer.getTotalDuration().toMillis();
                mediaPlayer.seek(javafx.util.Duration.millis(seekTime));
            }
        });

        // allow clicking on slider to jump
        progressBar.setOnMouseReleased(e -> {
            if (mediaPlayer.getTotalDuration() != null) {
                double seekTime = progressBar.getValue() / 100.0 * mediaPlayer.getTotalDuration().toMillis();
                mediaPlayer.seek(javafx.util.Duration.millis(seekTime));
            }
        });

        // Toggle Play/Pause Button
        Button playPauseBtn = new Button("▶"); // initial state: paused
        playPauseBtn.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playPauseBtn.setText("▶");
            } else {
                mediaPlayer.play();
                playPauseBtn.setText("⏸");
            }
        });

        // Controls Layout
        VBox controlBox = new VBox(10, progressBar, playPauseBtn);
        controlBox.setPadding(new Insets(10, 20, 10, 20));
        controlBox.setAlignment(Pos.CENTER);

        // Root Layout
        BorderPane root = new BorderPane();
        root.setCenter(mediaView);
        root.setBottom(controlBox);

        // Set background to black
        root.setStyle("-fx-background-color: black;");

        // Scene
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        stage.setTitle("Simple JavaFX Video Player");
        stage.setScene(scene);
        stage.show();

        // video adjusts to window size
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitWidth(newVal.doubleValue());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitHeight(newVal.doubleValue() - 100); // adjust for controls
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
