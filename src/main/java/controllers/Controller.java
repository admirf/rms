package controllers;

import javafx.stage.Stage;
import model.User;

/**
 * Created by admir on 26.12.2016..
 */
public interface Controller {
    void setStage(Stage s);
    void setUser(User u);
}
