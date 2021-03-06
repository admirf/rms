package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Job;
import model.User;
import repository.DefaultSessionFactory;
import repository.UserRepository;
import utility.Logger;


/**
 * Handles the initial login of a user
 */

public class LoginController implements Controller {
    private UserRepository userRepository;
    private Stage stage;
    private User user;

    @FXML
    private TextField email;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setUser(User u) { this.user = u; }

    @FXML
    public void login() {
        try {
            setUser(userRepository.readUserByCredentials(email.getText(), passwordField.getText()));
            System.out.println(user.getName());
            Controller controller = null;
            String fxmlUrl = "";
            switch (user.getJob()) {
                case Job.MANAGER:
                    controller = new ManagerController();
                    fxmlUrl = "/manager.fxml";
                    break;
                case Job.WAITER:
                    controller = new WaiterController();
                    fxmlUrl = "/waiter.fxml";
                    stage.setMinWidth(300);
                    stage.setMaxWidth(400);
                    break;
                case Job.RECEPTIONIST:
                    controller = new ReceptionistController();
                    fxmlUrl = "/TABLES.fxml";
                    stage.setMaxWidth(600);
                    stage.setMaxHeight(480);
                    break;
                case Job.COOK:
                    controller = new CookController();
                    fxmlUrl = "/chef.fxml";
                    break;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlUrl));
            controller.setUser(user);
            controller.setStage(stage);
            loader.setController(controller);
            Parent root = loader.load();
            stage.setScene(new Scene(root));

        }
        catch (Exception e) {
            e.printStackTrace(Logger.getInstance().getWriter());
            messageLabel.setText("Could not log in.");
        }
    }

    @FXML
    public void initialize() {
        userRepository = new UserRepository(DefaultSessionFactory.getInstance());
    }

}
