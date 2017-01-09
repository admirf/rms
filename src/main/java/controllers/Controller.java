package controllers;

import javafx.stage.Stage;
import model.User;

/**
 * Controller interface, used mainly for the purpose of creating polymorphic methods on controllers
 */
public interface Controller {
    void setStage(Stage s);
    void setUser(User u);
}
