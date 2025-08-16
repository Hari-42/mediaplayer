package src.sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

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

        // Buttons
        Button playBtn = new Button("▶ Play");
        playBtn.setOnAction(e -> mediaPlayer.play());

        Button pauseBtn = new Button("⏸ Pause");
        pauseBtn.setOnAction(e -> mediaPlayer.pause());

        Button stopBtn = new Button("⏹ Stop");
        stopBtn.setOnAction(e -> mediaPlayer.stop());

        // Progress Slider
        Slider progressBar = new Slider();
        progressBar.setMin(0);
        progressBar.setMax(100);
        progressBar.setValue(0);

        // allow it to grow with window size
        HBox.setHgrow(progressBar, Priority.ALWAYS);
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

        // Layout
        BorderPane root = new BorderPane();
        root.setCenter(mediaView);

        HBox controls = new HBox(10, playBtn, pauseBtn, stopBtn, progressBar);
        controls.setSpacing(10);
        controls.setPadding(new Insets(10, 20, 10, 20)); // top, right, bottom, left

        root.setBottom(controls);

        // Scene
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Simple JavaFX Video Player");
        stage.setScene(scene);
        stage.show();

        // video adjusts to window size
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitWidth(newVal.doubleValue());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitHeight(newVal.doubleValue() - 60); // adjust for controls
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
