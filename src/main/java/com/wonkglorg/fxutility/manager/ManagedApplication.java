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

public abstract class ManagedApplication extends javafx.application.Application {
    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<Class<? extends Node>, Map<String, ? super Node>> nodeMap = new HashMap<>();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
    }

    public abstract void applicationStart(Stage stage) throws IOException;

    /**
     * Adds a new Scene
     *
     * @param name
     * @param fxmlPath
     */
    public void addScene(String name, String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        scenes.put(name, scene);
    }

    /**
     * Loads a scene (making it visible if it has already been added)
     *
     * @param name
     */
    public void loadScene(String name) {
        loadScene(primaryStage, name);
    }

    /**
     * Loads a scene (making it visible if it has already been added)
     *
     * @param stage
     * @param name
     */
    public void loadScene(Stage stage, String name) {
        Objects.requireNonNull(stage);
        stage.setScene(scenes.get(name));
        stage.show();
    }


    /**
     * Gets a node from the map with a specific type
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends Node> T getNode(String key, Class<T> clazz) {
        Node node = (Node) nodeMap.get(clazz).get(key);
        if (clazz.isInstance(node)) {
            return clazz.cast(node);
        }
        return null;
    }

    /**
     * Adds a new node
     *
     * @param name           name of the node to be accessed in the map
     * @param fxmlPathString path to the fxml file
     * @throws IOException
     */
    public void addNode(String name, String fxmlPathString) throws IOException {
        Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPathString)));
        nodeMap.putIfAbsent(node.getClass(), new HashMap<>());
        nodeMap.get(node.getClass()).put(name, node);

    }
}
