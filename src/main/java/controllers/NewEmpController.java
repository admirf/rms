package controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Job;
import model.User;
import repository.DefaultSessionFactory;
import repository.UserRepository;
import utility.Hasher;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admir on 26.12.2016..
 */
public class NewEmpController implements Controller {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private User user;

    @FXML
    private Button addBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField yearBirthField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label messageLabel;

    private Stage stage;

    @Override
    public void setUser(User u) { this.user = u; }

    @Override
    public void setStage(Stage s) {
        stage = s;
    }

    @FXML
    public void initialize() {
        comboBox.getSelectionModel().selectFirst();

        addBtn.setOnAction(event -> {
            if(isUserDataValid()) {
                try {
                    setUser(new User());
                    user.setName(nameField.getText());
                    user.setSurname(surnameField.getText());
                    user.setPassword(Hasher.toMD5(passwordField.getText()));
                    user.setEmail(emailField.getText());
                    user.setBirthDate(simpleDateFormat.parse(yearBirthField.getText()));
                    user.setStarted(new Date());
                    user.setMonthlyPay(800);
                    user.setJob(comboBox.getSelectionModel().getSelectedIndex() + 1);

                    UserRepository userRepository = new UserRepository(DefaultSessionFactory.getInstance());

                    if(!userRepository.emailExists(user.getEmail())) {
                        userRepository.create(user);
                        messageLabel.setTextFill(Color.ROYALBLUE);
                        messageLabel.setText("User created successfully");
                        PauseTransition delay = new PauseTransition(Duration.millis(2000));
                        delay.setOnFinished(e -> stage.close());
                        delay.play();
                    }
                    else {
                        messageLabel.setText("User with same email already exists.");
                    }

                }
                catch (NoSuchAlgorithmException e) {

                }
                catch (ParseException e) {

                }
                catch(Exception e) {
                    messageLabel.setText("We are sorry. Something went wrong.");
                }


            }
            else {
                messageLabel.setText("Please make sure that your input is correct. (Birthdate format: D/M/Y)");
            }
        });
    }

    private boolean isYearValid(String s) {
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            System.out.println(date);
        }
        catch (ParseException e) {
            return false;
        }
        return true;
    }

    private boolean isUserDataValid() {

        System.out.println(yearBirthField.getText().isEmpty());

        if (nameField.getText().isEmpty()
                || surnameField.getText().isEmpty()
                || emailField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || yearBirthField.getText().isEmpty()
                || !isYearValid(yearBirthField.getText())
                ) {
            return false;
        }
        return true;
    }
}
