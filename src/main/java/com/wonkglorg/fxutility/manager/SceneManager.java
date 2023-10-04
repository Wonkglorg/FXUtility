package com.wonkglorg.fxutility.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class SceneManager {
    private static SceneManager sceneManager;
    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<Class<? extends Node>, Map<String, ? super Node>> nodeMap = new HashMap<>();
    private Stage primaryStage;

    public static SceneManager getInstance() {
        if (sceneManager == null) {
            sceneManager = new SceneManager();
        }
        return sceneManager;
    }

    public void addScene(String name, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scenes.put(name, scene);
    }

    public void loadScene(Stage primaryStage, String name) {
        Objects.requireNonNull(primaryStage);
        primaryStage.setScene(scenes.get(name));
        primaryStage.show();
    }

    public void loadScene(String name) {
        loadScene(primaryStage, name);
    }

    public void addNode(String name, String fxmlPathString) throws IOException {
        Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPathString)));
        nodeMap.putIfAbsent(node.getClass(), new HashMap<>());
        nodeMap.get(node.getClass()).put(name, node);

    }

    public <T extends Node> T getNode(String key, Class<T> clazz) {
        Node node = (Node) nodeMap.get(clazz).get(key);
        if (clazz.isInstance(node)) {
            return clazz.cast(node);
        }
        return null;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = Objects.requireNonNull(primaryStage);
    }

}