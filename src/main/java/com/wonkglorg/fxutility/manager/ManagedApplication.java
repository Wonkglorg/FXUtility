package com.wonkglorg.fxutility.manager;

import com.wonkglorg.fxutility.manager.util.ChooserUtil;
import javafx.css.Stylesheet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ManagedApplication extends javafx.application.Application {
    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<Class<? extends Node>, Map<String, ? super Node>> nodeMap = new HashMap<>();
    private Stage primaryStage;
    private final Map<String, Stylesheet> cssMap = new HashMap<>();
    private static ManagedApplication instance;
    private static ChooserUtil chooserUtil;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.chooserUtil = new ChooserUtil(primaryStage);
        instance = this;
        applicationStart(primaryStage);
    }

    /**
     * Gets called when the application starts right after setting the primary stage
     *
     * @param stage
     * @throws IOException
     */
    public abstract void applicationStart(Stage stage) throws IOException;

    /**
     * Adds a new Scene
     *
     * @param name
     * @param fxmlPath
     */
    public void addScene(String name, String fxmlPath) {

        URL resource = getResource(validatePath(fxmlPath, name));
        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Could not load scene: " + fxmlPath, e);
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

        if (!nodeMap.containsKey(clazz)) {
            return null;
        }

        Node node = (Node) nodeMap.get(clazz).get(key);
        if (clazz.isInstance(node)) {
            return clazz.cast(node);
        }
        return null;
    }

    /**
     * Adds a new node
     *
     * @param name     name of the node to be accessed in the map
     * @param fxmlPath path to the fxml file
     * @throws IOException
     */
    public void addNode(String name, String fxmlPath) {

        URL resource = getResource(validatePath(fxmlPath, name));

        Node node = null;
        try {
            node = FXMLLoader.load(resource);
        } catch (IOException e) {
            throw new RuntimeException("Could not load node: " + fxmlPath, e);
        }
        addNodeIfAbsent(node, name);
    }

    /**
     * Adds a new node
     *
     * @param node
     * @param name
     */
    public void addNode(Node node, String name) {
        addNodeIfAbsent(node, name);
    }

    /**
     * Utility function to add a node to the map if it is not already present
     *
     * @param node
     * @param name
     */
    private void addNodeIfAbsent(Node node, String name) {
        nodeMap.putIfAbsent(node.getClass(), new HashMap<>());
        nodeMap.get(node.getClass()).putIfAbsent(name, node);
    }

    public void addCss(String name, Stylesheet stylesheet) {
        cssMap.put(name, stylesheet);
    }

    public void addCss(String name, String path) {
        Stylesheet stylesheet = null;
        try {
            stylesheet = Stylesheet.loadBinary(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cssMap.put(name, stylesheet);
    }

    /**
     * Sets a css style for the specified scene
     *
     * @param sceneName
     * @param cssName
     */
    public void setCssForScene(String sceneName, String cssName) {

        if (!scenes.containsKey(sceneName)) throw new RuntimeException("Scene not found: " + sceneName);
        Scene scene = scenes.get(sceneName);

        if (!cssMap.containsKey(cssName)) throw new RuntimeException("CSS not found: " + cssName);

        scene.getStylesheets().add(cssMap.get(cssName).getUrl());
    }

    public Stylesheet getCss(String name) {
        return cssMap.get(name);
    }

    public static ManagedApplication getInstance() {
        return instance;
    }


    /**
     * Validates a path and throws an exception if it is invalid
     *
     * @param path
     * @param name
     * @return returns the path and prepends a / if non is present.
     */
    private String validatePath(String path, String name) {
        if (path == null) throw new NullPointerException("Path for: \"" + name + "\" cannot be null!");
        if (path.startsWith("/")) return path;
        return "/" + path;
    }

    private URL getResource(String path) {
        URL url = getClass().getResource(path);
        if (url == null) throw new NullPointerException("Could not load resource: \"" + path + "\" not a valid path.");
        return url;
    }

    public Stage getStage() {
        return primaryStage;
    }

    public static ChooserUtil getChooser() {
        return chooserUtil;
    }
}