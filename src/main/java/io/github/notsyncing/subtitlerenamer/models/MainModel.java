package io.github.notsyncing.subtitlerenamer.models;

import io.github.notsyncing.subtitlerenamer.constants.PatternType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainModel {
    private StringProperty currentVideoDirectory = new SimpleStringProperty();
    private StringProperty videoPattern = new SimpleStringProperty();
    private StringProperty subtitlePattern = new SimpleStringProperty();
    private PatternType videoPatternType = PatternType.Simple;
    private PatternType subtitlePatternType = PatternType.Simple;
    private BooleanProperty dryRun = new SimpleBooleanProperty(true);
    private BooleanProperty running = new SimpleBooleanProperty(false);
    private BooleanProperty useCopy = new SimpleBooleanProperty(true);

    public String getCurrentVideoDirectory() {
        return currentVideoDirectory.get();
    }

    public StringProperty currentVideoDirectoryProperty() {
        return currentVideoDirectory;
    }

    public void setCurrentVideoDirectory(String currentVideoDirectory) {
        this.currentVideoDirectory.set(currentVideoDirectory);
    }

    public String getVideoPattern() {
        return videoPattern.get();
    }

    public StringProperty videoPatternProperty() {
        return videoPattern;
    }

    public void setVideoPattern(String videoPattern) {
        this.videoPattern.set(videoPattern);
    }

    public String getSubtitlePattern() {
        return subtitlePattern.get();
    }

    public StringProperty subtitlePatternProperty() {
        return subtitlePattern;
    }

    public void setSubtitlePattern(String subtitlePattern) {
        this.subtitlePattern.set(subtitlePattern);
    }

    public PatternType getVideoPatternType() {
        return videoPatternType;
    }

    public void setVideoPatternType(PatternType videoPatternType) {
        this.videoPatternType = videoPatternType;
    }

    public PatternType getSubtitlePatternType() {
        return subtitlePatternType;
    }

    public void setSubtitlePatternType(PatternType subtitlePatternType) {
        this.subtitlePatternType = subtitlePatternType;
    }

    public boolean isDryRun() {
        return dryRun.get();
    }

    public BooleanProperty dryRunProperty() {
        return dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun.set(dryRun);
    }

    public boolean isRunning() {
        return running.get();
    }

    public BooleanProperty runningProperty() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running.set(running);
    }

    public boolean isUseCopy() {
        return useCopy.get();
    }

    public BooleanProperty useCopyProperty() {
        return useCopy;
    }

    public void setUseCopy(boolean useCopy) {
        this.useCopy.set(useCopy);
    }
}
