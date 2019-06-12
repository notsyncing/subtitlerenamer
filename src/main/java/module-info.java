module subtitlerenamer.app {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports io.github.notsyncing.subtitlerenamer;
    exports io.github.notsyncing.subtitlerenamer.controllers;

    opens io.github.notsyncing.subtitlerenamer.controllers;
}