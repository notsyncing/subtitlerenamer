package io.github.notsyncing.subtitlerenamer.business;

import io.github.notsyncing.subtitlerenamer.constants.PatternType;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Renamer {
    public static class Builder {
        private String directory;
        private String videoPattern;
        private String subtitlePattern;
        private PatternType videoPatternType;
        private PatternType subtitlePatternType;
        private boolean dryRun;
        private boolean useCopy;

        public Builder onDirectory(String directory) {
            this.directory = directory;
            return this;
        }

        public Builder withVideoPattern(String pattern, PatternType type) {
            videoPattern = pattern;
            videoPatternType = type;
            return this;
        }

        public Builder withSubtitlePattern(String pattern, PatternType type) {
            subtitlePattern = pattern;
            subtitlePatternType = type;
            return this;
        }

        public Builder dryRun(boolean dryRun) {
            this.dryRun = dryRun;
            return this;
        }

        public Builder useCopy(boolean useCopy) {
            this.useCopy = useCopy;
            return this;
        }

        public Renamer build() {
            return new Renamer(this);
        }
    }

    private Builder params;
    private Consumer<String> logger;

    private Renamer(Builder params) {
        this.params = params;
    }

    public CompletableFuture<Void> startRename(Consumer<String> logger) {
        this.logger = logger;

        return CompletableFuture.runAsync(this::rename);
    }

    private void log(String text) {
        if (logger == null) {
            return;
        }

        logger.accept(text);
    }

    private void rename() {
        try {
            var videoPatternMatcher = PatternMatcher.create(params.videoPatternType);
            var subtitlePatternMatcher = PatternMatcher.create(params.subtitlePatternType);

            var dir = Paths.get(params.directory);

            log("Working directory: " + dir);

            var videoFileList = Files.list(dir)
                    .filter(p -> videoPatternMatcher.match(params.videoPattern, p.getFileName().toString()))
                    .collect(Collectors.toList());

            log("Matched " + videoFileList.size() + " video file(s).");

            for (var videoFile : videoFileList) {
                var number = videoPatternMatcher.getNumber(params.videoPattern, videoFile.getFileName().toString());
                var subtitleExpectedName = params.subtitlePattern.replaceAll("<number>", number);

                var subtitleFiles = Files.list(dir)
                        .filter(p -> subtitlePatternMatcher.match(subtitleExpectedName, p.getFileName().toString()))
                        .collect(Collectors.toList());

                if (subtitleFiles.isEmpty()) {
                    log("Expected file " + subtitleExpectedName + " not found!");
                    continue;
                }

                for (var subtitleFile : subtitleFiles) {
                    var videoFilename = videoFile.getFileName().toString();
                    var name = videoFilename.substring(0, videoFilename.lastIndexOf('.'));
                    var subtitleFilename = subtitleFile.getFileName().toString();
                    var newSubtitleFilename = name + subtitleFilename.substring(subtitleFilename.lastIndexOf('.'));
                    var newSubtitleFile = dir.resolve(newSubtitleFilename);

                    if (params.useCopy) {
                        if (!params.dryRun) {
                            Files.copy(subtitleFile, newSubtitleFile);
                        }

                        log("Copied subtitle file \"" + subtitleFile.getFileName() + "\" -> \"" + newSubtitleFilename + "\"");
                    } else {
                        if (!params.dryRun) {
                            Files.move(subtitleFile, newSubtitleFile);
                        }

                        log("Renamed subtitle file \"" + subtitleFile.getFileName() + "\" -> \"" + newSubtitleFilename + "\"");
                    }
                }
            }

            log("Done.");
        } catch (Exception e) {
            e.printStackTrace();
            log("An exception occurred: " + e.getMessage());
        }
    }
}
