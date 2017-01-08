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
import model.User;
import repository.DefaultSessionFactory;
import repository.ItemRepository;
import repository.OrderRepository;
import repository.TablesRepository;
import ui.MenuItemCell;

/**
 * Created by admir on 08.01.2017..
 */
public class WaiterController implements Controller {

    private OrderRepository orderRepository;
    private TablesRepository tablesRepository;
    private Stage stage;
    private User user;
    private ItemRepository itemRepository;

    @FXML
    private ListView<Item> menuListView;

    @FXML
    private Label messageLabel;

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
                e.printStackTrace();
            }
            Platform.runLater(() -> messageLabel.setText("Menu"));

        }).start();
    }
}
