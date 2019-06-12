package io.github.notsyncing.subtitlerenamer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SubtitleRenamerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("SubtitleRenamer");

        var parent = FXMLLoader.<Parent>load(getClass().getResource("/ui/main.fxml"));
        var scene = new Scene(parent);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
