package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import entities.Order;
import entities.Paczkamat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import services.DataSource;
import services.SessionStore;
import web.WebViewConnector;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab paczkamatsTab;

    @FXML
    private WebView paczkamatsWebView;

    @FXML
    private Tab ordersTab;

    @FXML
    private TableView<Order> sentOrdersTable;

    @FXML
    private GridPane orderDetailsGrid;

    @FXML
    private Button logoutBtn;

    @FXML
    private TableView<Order> paczkamatStatsTable;

    @FXML
    private DatePicker datePicker;

    @FXML
    private DatePicker datePickerOrders;

    @FXML
    private TableView<Order> receivedOrdersTable;

    @FXML
    private Label senderLabel;

    @FXML
    private Label recipientLabel;

    @FXML
    private Label senderPaczkamatLabel;

    @FXML
    private Label recipientPaczkamatLabel;


    private WebViewConnector webViewConnector;

    private Paczkamat adminPaczkamat;

    @FXML
    void onLogoutClicked(ActionEvent event) {
        SessionStore.setLoggedIn(false);
        showNewlayout("layout/login_screen.fxml", event);
    }

    @FXML
    void initialize() {
        webViewConnector = new WebViewConnector();
        setupWebView(paczkamatsWebView, "admin_map.html");

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        System.out.println("Tab Selection changed to " + t1.getText());
                    }
                }
        );

        webViewConnector.adminSetPaczkamatProperty().addListener((observableValue, oldPaczkamat, newPaczkamat) -> {
            adminPaczkamat = newPaczkamat;
            System.out.println(adminPaczkamat.getName());
            paczkamatStatsTable.setItems(DataSource.getOrders().filtered(order ->
                    order.getSenderStash().getPaczkamat().getName().equals(adminPaczkamat.getName())  ||
                    order.getReceiverStash().getPaczkamat().getName().equals(adminPaczkamat.getName())
            ));

        });

        datePicker.setOnAction(actionEvent -> {
            LocalDate date = datePicker.getValue();
            paczkamatStatsTable.setItems(DataSource.getOrders().filtered(order -> {
                LocalDateTime sendTime = order.getSendDatetime().toLocalDateTime();
                if (
                        (order.getSenderStash().getPaczkamat().getName().equals(adminPaczkamat.getName())
                                || order.getReceiverStash().getPaczkamat().getName().equals(adminPaczkamat.getName())) &&
                                sendTime.getYear() == date.getYear() &&
                                sendTime.getMonthValue() == date.getMonthValue() &&
                                sendTime.getDayOfMonth() == date.getDayOfMonth()
                ) {
                    // display only orders for this paczkamat and given day
                    return true;
                }

                return false;
            }));
        });

        paczkamatStatsTable.setItems(DataSource.getOrders().filtered(order -> order.getOrderStatus().equals("AWAITING_PICKUP")));

        sentOrdersTable.setItems(DataSource.getOrders().filtered(order -> order.getOrderStatus().equals("AWAITING_PICKUP") || order.getOrderStatus().equals("IN_DELIVERY")));

        sentOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, order, newOrder) -> {
            System.out.println("Selected: " + newOrder.getOrderStatus());
            senderLabel.setText(newOrder.getSender().getName());
            senderPaczkamatLabel.setText(newOrder.getSenderStash().getPaczkamat().getName());
            recipientLabel.setText(newOrder.getReceiver().getName());
            recipientPaczkamatLabel.setText(newOrder.getReceiverStash().getPaczkamat().getName());
        });

        receivedOrdersTable.setItems(DataSource.getOrders().filtered(order -> order.getOrderStatus().equals("IN_SHIPMENT") || order.getOrderStatus().equals("REALIZED") ));

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
