package main;

import controllers.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.DefaultSessionFactory;

/**
 * Created by admir on 25.11.2016..
 */
public class Driver extends Application {

    public static void main(String args[]) {
        Application.launch(Driver.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setStage(stage);
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.setTitle("RMS");
        stage.setScene(new Scene(root, 500, 500));
        stage.show();

        stage.setOnCloseRequest(e -> {
            DefaultSessionFactory.close();
            Platform.exit();
        });

    }
}
