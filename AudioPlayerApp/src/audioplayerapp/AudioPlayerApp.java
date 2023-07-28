import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class AudioPlayerApp extends Application {
    private MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String folderPath = "C:\\Users\\dell\\OneDrive\\Desktop\\audio_java\\Music";

        ListView<String> songList = new ListView<>();
        loadSongsFromFolder(folderPath, songList);

        songList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedSongPath = folderPath + File.separator + newValue;
                playAudio(selectedSongPath);
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.add(songList, 0, 0); // Add the songList to the gridPane

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Audio Player");
        primaryStage.show();
    }

    private void loadSongsFromFolder(String folderPath, ListView<String> songList) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isAudioFile(file)) {
                        String songName = file.getName();
                        songList.getItems().add(songName);
                    }
                }
            }
        }
    }

  private boolean isAudioFile(File file) {
    String fileName = file.getName();
    String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    return extension.equals("mp3");
}

    private void playAudio(String path) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
