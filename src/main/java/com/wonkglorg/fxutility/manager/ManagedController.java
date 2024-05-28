package com.wonkglorg.fxutility.manager;

import javafx.fxml.FXML;

/**
 * This class is a controller that is managed by the ManagedControllerManager. It is used to update the controller. and provide some useful methods.
 */
public abstract class ManagedController {

    /**
     * Calls an update on the controller
     */
    public abstract void update();

    @FXML
    void initialize() {
        init();
    }

    /**
     * This method is called after the FXML has been injected. Use this instead of a constructor to access fxml elements.
     */
    public abstract void init();

}
