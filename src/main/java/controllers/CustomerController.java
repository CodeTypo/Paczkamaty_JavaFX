package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
import java.util.ResourceBundle;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;
import services.DataSource;
import services.SessionStore;
import web.WebViewConnector;

public class CustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private TableView<Order> ordersListSentTo;

    @FXML
    private Text loggedInAs;

    @FXML
    private URL location;

    @FXML
    private Text SendTextHint;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab sendTab;

    @FXML
    private WebView orderWebView;

    @FXML
    private GridPane paczkamatPreviewGrid;

    @FXML
    private ComboBox<Customer> recipientComboBox;

    @FXML
    private ComboBox<String> dimensionComboBox;

    @FXML
    private Button orderBtn;

    @FXML
    private Text textMsg;

    @FXML
    private Text sendPaczkamatName;

    @FXML
    private ComboBox<Stash> sendStash;

    @FXML
    private Text receivePaczkamatName;

    @FXML
    private ComboBox<Stash> receiveStash;

    @FXML
    private Tab trackTab;

    @FXML
    private WebView trackWebView;

    @FXML
    private TableView<Order> ordersListReceivingFrom;

    @FXML
    private Button logoutBtn;


    private WebViewConnector webViewConnector;

    private ObservableList<String> dimensions = FXCollections.observableArrayList
            ("Small", "Medium", "Large");

    private Paczkamat sendPaczkamat;
    private Paczkamat receivePaczkamat;

    @FXML
    void onLogoutClicked(ActionEvent event) {
        DataSource.setLoggedUser(null);
        SessionStore.setLoggedIn(false);
        System.out.println(SessionStore.isLoggedIn());
        showNewlayout("layout/login_screen.fxml", event);
    }

    @FXML
    void onOrderClicked(ActionEvent event) {
        Customer customer = SessionStore.getUser();

        Customer recipient = recipientComboBox.getValue();
        String dimension = dimensionComboBox.getValue();
        Stash senderStash = sendStash.getValue();
        Stash receiverStash = receiveStash.getValue();

        if (recipient == null) {
            textMsg.setText("Choose recipient to send order");
            return;
        }

        if (dimension == null) {
            textMsg.setText("Choose package dimension to send order");
            return;
        }

        if (sendPaczkamat == null) {
            textMsg.setText("Choose send paczkamat from map to send order");
            return;
        }

        if (receivePaczkamat == null) {
            textMsg.setText("Choose recipient paczkamat from map to send order");
            return;
        }

        if (senderStash == null) {
            textMsg.setText("Choose sender stash to send order");
            return;
        }

        if (receiverStash == null) {
            textMsg.setText("Choose receiver stash to send order");
            return;
        }

        Order order = new Order();
        order.setSender(customer);
        order.setOrderStatus("AWAITING_PICKUP");
        order.setReceiver(recipient);
//        Timestamp sendTime = new Timestamp(Calendar.getInstance().getTime().getTime());
        Timestamp sendTime = Timestamp.from(Instant.now());
        order.setSendDatetime(sendTime);
        ZonedDateTime zonedDateTime = sendTime.toInstant().atZone(ZoneId.of("UTC"));
        Timestamp receiveTime = Timestamp.from(zonedDateTime.plus(3, ChronoUnit.DAYS).toInstant());
//        Timestamp receiveTime = Timestamp.from(sendTime.plus(3, ChronoUnit.DAYS).toInstant());
        order.setReceiveDatetime(receiveTime);
//        order.setReceiveDatetime(sendTime);

        BigDecimal price = BigDecimal.ONE;
        switch (dimension) {
            case "Small" -> price = BigDecimal.valueOf(9.90);
            case "Medium" -> price = BigDecimal.valueOf(16.90);
            case "Large" -> price = BigDecimal.valueOf(28.90);
            default -> textMsg.setText("Wrong dimension");
        }

        order.setPrice(price);

        order.setSenderStash(senderStash);
        order.setReceiverStash(receiverStash);

        DataSource.addOrder(order);

        senderStash.getOrdersToSend().add(order);
        receiverStash.getOrdersToReceive().add(order);
        customer.getOrdersAsSender().add(order);
        recipient.getOrdersAsReceiver().add(order);

        System.out.println("Sent order to: " + recipient.getName());
    }

    @FXML
    void initialize() {
        webViewConnector = new WebViewConnector();
        setupWebView(orderWebView, "customer_map.html");
        //orderWebView.setBlendMode(BlendMode.HARD_LIGHT);
        //setupWebView(trackWebView, "customer_map.html");
        loggedInAs.setText("Logged in as: " + SessionStore.getUser().toString());
        dimensionComboBox.setItems(dimensions);
        recipientComboBox.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {

            @Override
            public ListCell<Customer> call(ListView<Customer> customerListView) {

                final ListCell<Customer> cell = new ListCell<Customer>(){
                    @Override
                    protected void updateItem(Customer customer, boolean b) {
                        super.updateItem(customer, b);

                        if (customer != null) {
                            setText(customer.getName() + " " + customer.getLastName());
                        } else {
                            setText(null);
                        }
                    }
                };

                return cell;
            }
        });

        recipientComboBox.setItems(DataSource.getCustomers());

        webViewConnector.receivePaczkamatProperty().addListener((observableValue, oldPaczkamat, newPaczkamat) -> {
            receivePaczkamat = newPaczkamat;
            receivePaczkamatName.setText(newPaczkamat.getName());
            SendTextHint.setText("Uzupełnij dane i kliknij \"zamów\" lub wybierz inny paczkamat nadawczy.");
            receiveStash.setItems(FXCollections.observableArrayList(receivePaczkamat.getStashes()));

            receiveStash.setCellFactory(new Callback<ListView<Stash>, ListCell<Stash>>() {

                @Override
                public ListCell<Stash> call(ListView<Stash> stashListView) {
                    final ListCell<Stash> cell = new ListCell<Stash>(){
                        @Override
                        protected void updateItem(Stash stash, boolean b) {
                            super.updateItem(stash, b);

                            if (stash != null) {
                                String stashObjectId = stash.toString();
                                setText(stashObjectId.split("entities.")[1]);
                            } else {
                                setText(null);
                            }
                        }
                    };

                    return cell;
                }

            });

        });

        webViewConnector.sendPaczkamatProperty().addListener((observableValue, oldPaczkamat, newPaczkamat) -> {
            sendPaczkamat = newPaczkamat;
            sendPaczkamatName.setText(newPaczkamat.getName());
            SendTextHint.setText("Wybierz paczkamat, w którym odbiorca odbierze paczkę");
            sendStash.setItems(FXCollections.observableArrayList(sendPaczkamat.getStashes()));

            sendStash.setCellFactory(new Callback<ListView<Stash>, ListCell<Stash>>() {

                @Override
                public ListCell<Stash> call(ListView<Stash> stashListView) {
                    final ListCell<Stash> cell = new ListCell<Stash>(){
                        @Override
                        protected void updateItem(Stash stash, boolean b) {
                            super.updateItem(stash, b);

                            if (stash != null) {
                                String stashObjectId = stash.toString();
                                setText(stashObjectId.split("entities.")[1]);
                            } else {
                                setText(null);
                            }
                        }
                    };
//c
                    return cell;
                }

            });
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        System.out.println("Tab Selection changed to " + t1.getText());

                        if (t1.getId().equals("trackTab")) {
                            System.out.println(SessionStore.getUser().getName());
                            Collection<Order> ordersAsSender   = SessionStore.getUser().getOrdersAsSender();
                            Collection<Order> ordersAsReceiver = SessionStore.getUser().getOrdersAsReceiver();

                            ObservableList<Order> ordersSent = FXCollections.observableArrayList(ordersAsSender);
                            ObservableList<Order> ordersReceived = FXCollections.observableArrayList(ordersAsReceiver);
//                            orders.addAll(ordersAsReceiver);

                            ordersListSentTo.setItems(ordersSent);
                            ordersListReceivingFrom.setItems(ordersReceived);
                            System.out.println("Track tab");

                        }


                    }
                }
        );




    }

    void setupWebView(WebView webView, String htmlFile) {
        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("app", webViewConnector);
                });

        webEngine.load(getClass().getResource("/webview/" + htmlFile).toString());
    }

    private void showNewlayout(String path, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide current window
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
