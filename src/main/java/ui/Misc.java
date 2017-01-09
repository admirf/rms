package ui;

import controllers.Controller;
import controllers.NewEmpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Utility class containing miscellaneous methods
 */
public class Misc {

    public static <T extends Controller> void openWindow(Class<T> classtype, Stage stage, String fxmlControllerURL) throws IOException, IllegalAccessException, InstantiationException {
        FXMLLoader loader = new FXMLLoader(classtype.getClassLoader().getResource(fxmlControllerURL));
        T controller = (T) classtype.newInstance();
        controller.setStage(stage);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Treba sredit ovaj exception handling
     */
    public static void openUserCreationWindow()  {
        try {
            Misc.<NewEmpController> openWindow(NewEmpController.class, new Stage(), "newEmp.fxml");
        } catch (IllegalAccessException e) {
            e.printStackTrace(Logger.getInstance().getWriter());
        } catch (IOException e) {
            e.printStackTrace(Logger.getInstance().getWriter());
        } catch (InstantiationException e) {
            e.printStackTrace(Logger.getInstance().getWriter());
        }


    }
}
