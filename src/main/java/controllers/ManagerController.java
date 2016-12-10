package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

/**
 * Created by admir on 10.12.2016..
 */
public class ManagerController {

    @FXML
    private Label label1;

    private User user;
    private Stage stage;

    public void setUser(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        label1.setText(user.getName());
    }
}
