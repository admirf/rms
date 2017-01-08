package controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Item;
import model.Job;
import model.User;
import repository.DefaultSessionFactory;
import repository.ItemRepository;
import repository.UserRepository;
import ui.Misc;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admir on 10.12.2016..
 */
public class ManagerController implements Controller {

    @FXML
    private Button newEmpBtn;

    @FXML
    private Button remove;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label role;

    @FXML
    private Label pass;

    @FXML
    private ListView<User> list;

    @FXML
    private ListView<Item> menuListView;

    @FXML
    private Button createMenuItemBtn;

    @FXML
    private Button saveItemChangesBtn;

    @FXML
    private Button deleteItemBtn;

    @FXML
    private TextField imgUrlField;

    @FXML
    private TextField menuItemNameField;

    @FXML
    private TextField menuItemPriceField;

    @FXML
    private TextField menuItemCostField;

    @FXML
    private Label menuCreationMessageLabel;

    @FXML
    private Label itemName;

    @FXML
    private TextField itemPrice;

    @FXML
    private TextField itemCost;

    @FXML
    private ImageView itemImage;

    private List<User> li;
    private ObservableList<User> obsList;
    private ObservableList<Item> itemList;

    private User user;
    private User selectedUser;
    private Item selectedItem;
    private Stage stage;
    private UserRepository userRepository;
    private ItemRepository itemRepository;

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Updates the right side of the view according to the change of selected user
     */
    private void updateSelectedUserView() {
        name.setText(selectedUser.getName() + " " + selectedUser.getSurname());
        id.setText("" + selectedUser.getID());
        role.setText(Job.toString[selectedUser.getJob()]);
        pass.setText(selectedUser.getPassword());
    }

    /**
     * Updates the right side of the view according to the change of selected item
     */
    private void updateSelectedItemView() {
        itemName.setText(selectedItem.getName());
        itemCost.setText("" + selectedItem.getCost());
        itemPrice.setText("" + selectedItem.getPrice());
        itemImage.setImage(new Image(selectedItem.getImgUrl()));
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {

        userRepository = new UserRepository(DefaultSessionFactory.getInstance());
        itemRepository = new ItemRepository(DefaultSessionFactory.getInstance());
        li = userRepository.readAllUsers();
        selectedUser = null;
        selectedItem = null;
        obsList = FXCollections.observableArrayList(li);
        itemList = FXCollections.observableArrayList(itemRepository.readAllItems());



        list.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    Platform.runLater(() -> {
                        setText(null);
                    });
                } else {
                    Platform.runLater(() -> {
                        setText(item.getName() + " " + item.getSurname());
                    });

                }
            }
        });

        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                selectedUser = newValue;
                Platform.runLater(() -> {
                    updateSelectedUserView();
                });

            }
        });

        list.setItems(obsList);

        menuListView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    Platform.runLater(() -> {
                        setText(null);
                    });
                } else {
                    Platform.runLater(() -> {
                        setText(item.getName());
                    });

                }
            }
        });

        menuListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
                selectedItem = newValue;
                Platform.runLater(() -> {
                    updateSelectedItemView();
                });

            }
        });

        menuListView.setItems(itemList);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    li = userRepository.readAllUsers();

                    // all elements in obsList that are not in li
                    List<User> inObs = new ArrayList<>(obsList);
                    inObs.removeAll(li);

                    // all elements in li that are not in obsList
                    List<User> inLi = new ArrayList<>(li);
                    inLi.removeAll(obsList);

                    obsList.removeAll(inObs);
                    obsList.addAll(inLi);
                }
                catch(Exception e) {
                    this.cancel();
                    timer.cancel();
                }


            }
        };
        timer.schedule(task, 0, 1000);

        remove.setOnAction(event -> {
            userRepository.delete(selectedUser.getID());
        });

        newEmpBtn.setOnAction(event -> {
            Misc.openUserCreationWindow();
        });

        createMenuItemBtn.setOnAction(event -> {
            if(isMenuItemDataValid()) {
                Item item = new Item();
                item.setName(menuItemNameField.getText());
                item.setCost(Double.parseDouble(menuItemCostField.getText()));
                item.setPrice(Double.parseDouble(menuItemPriceField.getText()));
                item.setImgUrl(imgUrlField.getText());

                try {
                    int ID = itemRepository.create(item);
                    item.setID(ID);
                    itemList.add(item);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    showMessage("We are sorry, something went unexpectedly wrong.");
                    return;
                }

                showMessage(item.getName() + " successfully added to menu.");
            }
            else {
                showMessage("Please make sure that your input is valid.");
            }
        });

        deleteItemBtn.setOnAction((event) -> {
            int ID = menuListView.getSelectionModel().getSelectedItem().getID();
            int index = menuListView.getSelectionModel().getSelectedIndex();
            if(itemRepository.delete(ID))
                itemList.remove(index);
        });

        saveItemChangesBtn.setOnAction((event) -> {
            Item item = menuListView.getSelectionModel().getSelectedItem();
            int index = menuListView.getSelectionModel().getSelectedIndex();
            try {
                item.setPrice(Double.parseDouble(itemPrice.getText()));
                item.setCost(Double.parseDouble(itemCost.getText()));

                itemRepository.update(item);
                showMessage(item.getName() + " successfully updated.");
            }
            catch(NumberFormatException e) {
                e.printStackTrace();
                showMessage("Please make sure that your input is valid.");
            }

        });
    }


    public void showMessage(String s) {
        Thread th = new Thread(() -> {
            Platform.runLater(() -> menuCreationMessageLabel.setText(s));
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                Platform.runLater(() -> menuCreationMessageLabel.setText("Menu Creation"));
            }

        });

        th.start();
    }


    public boolean isMenuItemDataValid() {

        try {
            Double.parseDouble(menuItemCostField.getText());
            Double.parseDouble(menuItemPriceField.getText());
        }
        catch(NumberFormatException e) {
            return false;
        }

        if(imgUrlField.getText().length() > 255
                || menuItemNameField.getText().length() > 255) return false;

        return true;
    }
}


