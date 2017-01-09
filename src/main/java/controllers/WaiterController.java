package controllers;

import com.sun.deploy.util.Waiter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Item;
import model.Notification;
import model.User;
import repository.*;
import ui.MenuItemCell;
import utility.Logger;

import java.util.*;

/**
 * Controller for the Waiter View represented by waiter.fxml
 */
public class WaiterController implements Controller {

    private OrderRepository orderRepository;
    private NotificationRepository notificationRepository;
    private TablesRepository tablesRepository;
    private Stage stage;
    private User user;
    private ItemRepository itemRepository;
    private Queue<Notification> notificationQueue;

    @FXML
    private ListView<Item> menuListView;

    @FXML
    private Label messageLabel;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<Integer> comboBox;

    @FXML
    private Button revokeButton;

    private ObservableList<Item> menuItems;

    @Override
    public void setUser(User user) { this.user = user; }

    @Override
    public void setStage(Stage s) {
        stage = s;
    }

    @FXML
    public void initialize() {
        notificationQueue = new LinkedList<>();
        notificationRepository = new NotificationRepository(DefaultSessionFactory.getInstance());
        tablesRepository = new TablesRepository(DefaultSessionFactory.getInstance());
        orderRepository = new OrderRepository(DefaultSessionFactory.getInstance());
        itemRepository = new ItemRepository(DefaultSessionFactory.getInstance());

        menuItems = FXCollections.observableArrayList();
        menuItems.setAll(itemRepository.readAllItems());

        menuListView.setItems(menuItems);
        menuListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> param) {
                MenuItemCell cell = new MenuItemCell();
                cell.setWaiterController(WaiterController.this);
                return cell;
            }
        });

        revokeButton.setOnAction(event -> {
            Integer i = comboBox.getSelectionModel().getSelectedItem();
            if(i != null) {
                if(orderRepository.deleteByTable(tablesRepository.readByName(i).getID())) {
                    showMessage("Successfully revoked table " + i, 1000);
                }
                else {
                    showMessage("We are sorry something went wrong", 1000);
                }
            }
            else {
                showMessage("Please select a table to revoke orders", 1000);
            }
        });

        searchButton.setOnAction(event -> {
            if(!menuItems.isEmpty()) {
                String searchString = searchField.getText();
                if(searchString.isEmpty()) return;
                Integer index = null;
                for(int i = 0; i < menuItems.size(); ++i) {
                    if(menuItems.get(i).getName().toLowerCase().equals(searchString.toLowerCase())) {
                        index = i;
                        break;
                    }
                }

                if(index != null) {
                    Item tmp = menuItems.get(0);
                    menuItems.set(0, menuItems.get(index));
                    menuItems.set(index, tmp);
                }
                else {
                    showMessage("No match for " + searchString + " found", 1000);
                }
            }

        });

        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if(!notificationQueue.isEmpty()) {
                        Notification notification = notificationQueue.remove();
                        showMessage(notification.getMessage(), 1000);
                        try {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace(Logger.getInstance().getWriter());
                        }
                    }
                }
                catch (Exception e) {
                    this.cancel();
                }

            }
        }, 0, 250);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    List<Notification> li = notificationRepository.readAllUnreadNotifications();
                    for(Notification not: li) {
                        notificationQueue.add(not);
                        not.setRead(true);
                        notificationRepository.update(not);
                    }
                }
                catch (Exception e) {
                    this.cancel();
                    timer.cancel();
                    timer2.cancel();
                }

            }
        }, 0, 1000);


    }


    public ComboBox<Integer> getComboBox() {
        return comboBox;
    }


    public void showMessage(String msg, int millis) {
        new Thread(() -> {
            Platform.runLater(() -> messageLabel.setText(msg));
            try {
                Thread.sleep(millis);
            }
            catch (InterruptedException e) {
                e.printStackTrace(Logger.getInstance().getWriter());
            }
            Platform.runLater(() -> messageLabel.setText("Menu"));

        }).start();
    }
}
