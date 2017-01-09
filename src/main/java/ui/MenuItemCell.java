package ui;

import controllers.WaiterController;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import model.Item;
import model.Order;
import model.Tables;
import repository.DefaultSessionFactory;
import repository.OrderRepository;
import repository.TablesRepository;

import javax.swing.*;
import java.beans.EventHandler;

/**
 * Class that represents the ListCell used in the Menu ListView of the Waiter Controller
 */
public class MenuItemCell extends ListCell<Item> {
    private OrderRepository orderRepository;
    private TablesRepository tablesRepository;
    private HBox hbox;
    private Label nameLabel;
    private Label priceLabel;
    private ImageView image;
    private Pane pane;
    private Button orderButton;
    private TextField quantityField;
    private Item lastItem;
    private WaiterController controller;

    /**
     * Constructor there everything gets assembled
     */
    public MenuItemCell() {
        super();
        hbox = new HBox();
        pane = new Pane();
        nameLabel = new Label("empty");
        priceLabel = new Label("empty");
        image = new ImageView();
        orderButton = new Button("Order");
        quantityField = new TextField();
        orderRepository = new OrderRepository(DefaultSessionFactory.getInstance());
        tablesRepository = new TablesRepository(DefaultSessionFactory.getInstance());

        hbox.setSpacing(8);
        hbox.setAlignment(Pos.CENTER_LEFT);
        image.setFitHeight(20);
        image.setFitWidth(20);
        quantityField.setMaxWidth(30);

        hbox.getChildren().addAll(image, nameLabel, pane, priceLabel, quantityField, orderButton);
        hbox.setHgrow(pane, Priority.ALWAYS);
        orderButton.setOnAction(event -> {
            if(controller != null) {
                Integer i = controller.getComboBox().getSelectionModel().getSelectedItem();
                if(i != null) {
                    double quantity = 1;
                    try {
                        quantity = Double.parseDouble(quantityField.getText());
                    }
                    catch (NumberFormatException e) {
                    }

                    Tables table = tablesRepository.readByName(i);
                    System.out.println(table.getID());
                    Order newOrder = new Order();
                    newOrder.setItem(lastItem);
                    newOrder.setQuantity(quantity);
                    newOrder.setTable(table);
                    Integer id = orderRepository.create(newOrder);

                    if(id == null) {
                        controller.showMessage("Something went wrong", 1000);
                    }
                    else {
                        controller.showMessage(String.format("You ordered %.2f %s", quantity, lastItem.getName()), 1000);
                    }
                }
                else {
                    controller.showMessage("You did not select any table", 1000);
                }
            }
            else {
                 throw new RuntimeException("Should not have happened.");
            }
        });
    }

    /**
     * Method used to set a reference to the controller from which it is being used
     * This is needed so we can access the showMessage method from the WaiterController class
     * @param controller
     */
    public void setWaiterController(WaiterController controller) {
        this.controller = controller;
    }

    /**
     * Method inherited from ListCell, defines how our view should be drawn
     * @param item
     * @param empty
     */
    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);
        if(empty) {
            lastItem = null;
            setGraphic(null);
        }
        else {
            lastItem = item;
            if(item != null) {
                nameLabel.setText(item.getName());
                priceLabel.setText("" + item.getPrice());
                image.setImage(new Image(item.getImgUrl()));
                setGraphic(hbox);
            }
        }
    }
}
