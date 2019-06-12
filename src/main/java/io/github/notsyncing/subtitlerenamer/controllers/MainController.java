package io.github.notsyncing.subtitlerenamer.controllers;

import io.github.notsyncing.subtitlerenamer.business.Renamer;
import io.github.notsyncing.subtitlerenamer.constants.PatternType;
import io.github.notsyncing.subtitlerenamer.models.MainModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField txtVideoDirectory;

    @FXML
    private TextField txtVideoPattern;

    @FXML
    private TextField txtSubtitlePattern;

    @FXML
    private ToggleGroup videoPatternSelect;

    @FXML
    private ToggleGroup subtitlePatternSelect;

    @FXML
    private TextArea txtLog;

    @FXML
    private CheckBox cbUseCopy;

    @FXML
    private CheckBox cbDryRun;

    @FXML
    private Button btnStart;

    private MainModel model = new MainModel();

    public MainController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtVideoDirectory.textProperty().bindBidirectional(model.currentVideoDirectoryProperty());
        txtVideoPattern.textProperty().bindBidirectional(model.videoPatternProperty());
        txtSubtitlePattern.textProperty().bindBidirectional(model.subtitlePatternProperty());
        cbDryRun.selectedProperty().bindBidirectional(model.dryRunProperty());
        cbUseCopy.selectedProperty().bindBidirectional(model.useCopyProperty());

        videoPatternSelect.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            model.setVideoPatternType(PatternType.valueOf(newValue.getUserData().toString()));
        });

        subtitlePatternSelect.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            model.setSubtitlePatternType(PatternType.valueOf(newValue.getUserData().toString()));
        });

        btnStart.disableProperty().bindBidirectional(model.runningProperty());
    }

    public void chooseVideoDirectory(ActionEvent event) {
        var chooser = new FileChooser();
        var file = chooser.showOpenDialog(mainPane.getScene().getWindow());

        if (file == null) {
            return;
        }

        var dir = file.getParentFile().getAbsolutePath();
        model.setCurrentVideoDirectory(dir);
        model.setVideoPattern(file.getName());
    }

    public void chooseSubtitleFile(ActionEvent event) {
        var chooser = new FileChooser();
        chooser.setInitialDirectory(new File(model.getCurrentVideoDirectory()));

        var file = chooser.showOpenDialog(mainPane.getScene().getWindow());

        if (file == null) {
            return;
        }

        model.setSubtitlePattern(file.getName());
    }

    public void start() {
        model.setRunning(true);

        var cf = new Renamer.Builder()
                .onDirectory(model.getCurrentVideoDirectory())
                .withVideoPattern(model.getVideoPattern(), model.getVideoPatternType())
                .withSubtitlePattern(model.getSubtitlePattern(), model.getSubtitlePatternType())
                .dryRun(model.isDryRun())
                .useCopy(model.isUseCopy())
                .build()
                .startRename(s -> Platform.runLater(() -> txtLog.appendText(s + "\n")));

        cf.thenAccept(r -> model.setRunning(false));
    }
}
