package controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Notification;
import model.Order;
import model.Payment;
import model.User;
import repository.DefaultSessionFactory;
import repository.NotificationRepository;
import repository.OrderRepository;
import repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * Controller class that handles all the logic regarding the cook view
 */
public class CookController implements Controller {

    private Stage stage;
    private User user;
    private Order selectedOrder;
    private ObservableList<Order> orders;
    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;
    private NotificationRepository notificationRepository;

    @FXML
    private ListView<Order> orderListView;

    @FXML
    private Label orderCost;

    @FXML
    private Label orderPrice;

    @FXML
    private Button finishButton;

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
        selectedOrder = null;
        orderRepository = new OrderRepository(DefaultSessionFactory.getInstance());
        notificationRepository = new NotificationRepository(DefaultSessionFactory.getInstance());
        paymentRepository = new PaymentRepository(DefaultSessionFactory.getInstance());

        orders = FXCollections.observableArrayList();
        orders.setAll(orderRepository.readAllOrders());

        orderListView.setCellFactory(param -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    Platform.runLater(() -> {
                        setText(null);
                    });
                } else {
                    Platform.runLater(() -> {
                        setText(item.getQuantity() + "x " + item.getItem().getName());
                    });

                }
            }
        });

        orderListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                selectedOrder = newValue;
                Platform.runLater(() -> updateSelectedOrderView());
            }
        });

        orderListView.setItems(orders);

        finishButton.setOnAction(event -> {
            Notification notification = new Notification();
            notification.setMessage(selectedOrder.getQuantity() + "x " + selectedOrder.getItem().getName() + " on table " + selectedOrder.getTable().getName() + " is finished.");
            notificationRepository.create(notification);

            Payment transaction = new Payment();
            transaction.setValue(selectedOrder.getQuantity() * (selectedOrder.getItem().getPrice() - selectedOrder.getItem().getCost()));
            paymentRepository.create(transaction);

            orderRepository.delete(selectedOrder.getID());
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    List<Order> li = orderRepository.readAllOrders();

                    // all elements in obsList that are not in li
                    List<Order> inObs = new ArrayList<>(orders);
                    inObs.removeAll(li);

                    // all elements in li that are not in obsList
                    List<Order> inLi = new ArrayList<>(li);
                    inLi.removeAll(orders);

                    orders.removeAll(inObs);
                    orders.addAll(inLi);
                }
                catch(Exception e) {
                    this.cancel();
                    timer.cancel();
                }


            }
        }, 0, 1000);


    }


    public void updateSelectedOrderView() {
        if(selectedOrder !=  null) {
            orderCost.setText(selectedOrder.getItem().getCost() * selectedOrder.getQuantity() + "");
            orderPrice.setText(selectedOrder.getItem().getPrice() * selectedOrder.getQuantity() + "");
        }
    }
}
