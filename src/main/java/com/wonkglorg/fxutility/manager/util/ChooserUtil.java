package com.wonkglorg.fxutility.manager.util;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class ChooserUtil {

    private Stage stage;

    /**
     * Create a new ChooserUtil with a stage
     *
     * @param stage
     */
    public ChooserUtil(Stage stage) {
        this.stage = stage;
    }


    /**
     * Open a file chooser dialog
     *
     * @param defaultDirectory The default directory to open the dialog in
     * @param title            The title of the dialog
     * @return The selected file
     */
    public Optional<File> directoryChooser(File defaultDirectory, String title) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(defaultDirectory);

        File selectedDirectory = chooser.showDialog(stage);

        if (selectedDirectory == null || !selectedDirectory.isDirectory()) {
            return Optional.empty();
        }
        return Optional.of(selectedDirectory);
    }

    /**
     * Open a file chooser dialog
     *
     * @param defaultDirectory The default directory to open the dialog in
     * @param fileType         The file type filter
     * @return The selected file
     */
    public Optional<File> fileChooser(File defaultDirectory, String title, FileChooser.ExtensionFilter... fileType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(fileType);
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(defaultDirectory);

        File file = fileChooser.showOpenDialog(stage);

        if (file == null || !file.isFile()) {
            return Optional.empty();
        }
        return Optional.of(file);
    }

    /**
     * Open a file chooser dialog
     *
     * @param defaultDirectory The default directory to open the dialog in
     * @param title            The title of the dialog
     * @param initialFileTitle The initial file title
     * @param fileType         The file type filter
     * @return The selected file
     */
    public Optional<File> saveDialog(File defaultDirectory, String title, String initialFileTitle, FileChooser.ExtensionFilter... fileType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(defaultDirectory);
        fileChooser.setInitialFileName(initialFileTitle);
        if (fileType.length > 0) {
            fileChooser.getExtensionFilters().addAll(fileType);
            fileChooser.setSelectedExtensionFilter(fileType[0]);
        }

        var file = fileChooser.showSaveDialog(stage);
        return Optional.ofNullable(file);
    }

    /**
     * Open a file chooser dialog
     *
     * @param defaultDirectory The default directory to open the dialog in
     * @param fileType         The file type filter
     * @return The selected file
     */
    public Optional<File> fileChooser(File defaultDirectory, FileChooser.ExtensionFilter... fileType) {
        return fileChooser(defaultDirectory, "Select File", fileType);
    }

    /**
     * Open a file chooser dialog in the default user home directory
     *
     * @param fileType The file type filter
     * @return The selected file
     */
    public Optional<File> fileChooser(FileChooser.ExtensionFilter... fileType) {
        return fileChooser(new File(System.getProperty("user.home")), "Select File", fileType);
    }

    /**
     * Open a file chooser dialog in the default user directory with no file type filter set
     *
     * @return The selected file
     */
    public Optional<File> fileChooser() {
        return fileChooser(new File(System.getProperty("user.home")), "Select File");
    }


    /**
     * Open a file chooser dialog
     *
     * @param defaultDirectory The default directory to open the dialog in
     * @return The selected file
     */
    public Optional<File> directoryChooser(File defaultDirectory) {
        return directoryChooser(defaultDirectory, "Select Directory");
    }

    /**
     * Open a file chooser dialog in the default user directory
     *
     * @return The selected file
     */
    public Optional<File> directoryChooser() {
        return directoryChooser(new File(System.getProperty("user.home")));
    }


    /**
     * Sets the stage for the ChooserUtil
     *
     * @param stage The stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
