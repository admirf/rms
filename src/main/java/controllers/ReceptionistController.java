package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import model.Tables;
import model.User;
import repository.DefaultSessionFactory;
import repository.TablesRepository;
import utility.Logger;
import utility.RMSException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Receptionist Controller class for the tables view
 */
public class ReceptionistController implements Controller, EventHandler<MouseEvent> {

    private Stage stage;
    private User user;
    private TablesRepository tablesRepository;
    private List<Shape> li;

    @FXML
    private Color x1;
    @FXML
    private Rectangle rect0;
    @FXML
    private Rectangle rect1;
    @FXML
    private Rectangle rect2;
    @FXML
    private Rectangle rect3;
    @FXML
    private Rectangle rect4;
    @FXML
    private Rectangle rect5;
    @FXML
    private Rectangle rect6;
    @FXML
    private Rectangle rect7;

    @FXML
    private Circle circ0;
    @FXML
    private Circle circ1;
    @FXML
    private Circle circ2;


    @Override
    public void setStage(Stage s) {
        stage = s;
    }

    @Override
    public void setUser(User u) {
        user = u;
    }

    @FXML
    public void initialize() {

        tablesRepository = new TablesRepository(DefaultSessionFactory.getInstance());
        li = new ArrayList<>();

        rect0.setOnMouseClicked(this);
        rect1.setOnMouseClicked(this);
        rect2.setOnMouseClicked(this);
        rect3.setOnMouseClicked(this);
        rect4.setOnMouseClicked(this);
        rect5.setOnMouseClicked(this);
        rect6.setOnMouseClicked(this);
        rect7.setOnMouseClicked(this);

        circ0.setOnMouseClicked(this);
        circ1.setOnMouseClicked(this);
        circ2.setOnMouseClicked(this);

        li.add(rect0);
        li.add(rect1);
        li.add(rect2);
        li.add(rect3);
        li.add(rect4);
        li.add(rect5);
        li.add(rect6);
        li.add(rect7);
        li.add(circ0);
        li.add(circ1);
        li.add(circ2);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    List<Tables> tables = tablesRepository.readAllTables();
                    if(tables.size() != li.size()) throw new RMSException("Check your tables");

                    for(int i = 0; i < tables.size(); ++i) {
                        Shape shape = li.get(tables.get(i).getName());
                        if(tables.get(i).isOccupied()) {
                            shape.setFill(Paint.valueOf("RED"));
                        }
                        else {
                            shape.setFill(x1);
                        }
                    }
                }
                catch (RMSException e) {
                    System.out.println(e.getMessage());
                }
                catch(Exception e) {
                    this.cancel();
                    timer.cancel();
                }
            }
        }, 0, 500);


    }

    @Override
    public void handle(MouseEvent event) {
        try {
            Shape src = (Shape) event.getSource();
            Integer name = Integer.parseInt(src.getUserData().toString());
            System.out.println(name);

            // facebook optimistic optimization trick, enable it at your own risk
           /* if(src.getFill().equals(x1)) {
                src.setFill(Paint.valueOf("RED"));
            } else {
                src.setFill(x1);
            } */


            tablesRepository.toggleOccupied(name);
        }
        catch (NumberFormatException e) {
            e.printStackTrace(Logger.getInstance().getWriter());
        }
        catch (Exception e) {
            e.printStackTrace(Logger.getInstance().getWriter());
        }

    }
}
