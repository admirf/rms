package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import repository.DefaultSessionFactory;
import repository.UserRepository;


/**
 * Created by admir on 09.12.2016..
 */

/**
 * Za svaki izgled view tj. fxml file pravite controller file
 * gdje ide sva logika vezana za taj view
 * sta god da zelite da povezete iz fxml fajla sa nekim objektom
 * ili metodom stavljate @FXML anotaciju
 */

public class LoginController {
    private UserRepository userRepository;
    private Stage stage;

    @FXML
    private TextField email;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text actiontarget;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void login() {
        try {
            User user = userRepository.readUserByCredentials(email.getText(), passwordField.getText());
            System.out.println(user.getName());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/manager.fxml"));
            ManagerController managerController = new ManagerController();
            managerController.setUser(user);
            managerController.setStage(stage);
            loader.setController(managerController);
            Parent root = loader.load();
            stage.setScene(new Scene(root, 600, 400));

        }
        catch (Exception e) {
            e.printStackTrace();
            actiontarget.setText("Could not log in.");
        }
    }

    @FXML
    public void initialize() {
        userRepository = new UserRepository(DefaultSessionFactory.getInstance());
    }

}
