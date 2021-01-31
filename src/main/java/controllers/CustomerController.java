package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private URL location;

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
    private Text receivePaczkamatName;

    @FXML
    private Tab trackTab;

    @FXML
    private WebView trackWebView;

    @FXML
    private ListView<?> ordersList;

    @FXML
    private Button logoutBtn;

    private WebViewConnector webViewConnector;

    private ObservableList<String> dimensions = FXCollections.observableArrayList
            ("Small", "Medium", "Large");

    private Paczkamat sendPaczkamat;
    private Paczkamat receivePaczkamat;

    @FXML
    void onLogoutClicked(ActionEvent event) {
        SessionStore.setLoggedIn(false);
        showNewlayout("layout/login_screen.fxml", event);
    }

    @FXML
    void onOrderClicked(ActionEvent event) {
        Customer recipient = recipientComboBox.getValue();
        String dimension = dimensionComboBox.getValue().toString();

        Order order = new Order();
        order.setSender(SessionStore.getUser());
        order.setOrderStatus("AWAITING_PICKUP");
        order.setReceiver(recipient);
        order.setSendDatetime(new Timestamp(Calendar.getInstance().getTime().getTime()));

        BigDecimal price = BigDecimal.ONE;
        if (dimension.equals("SMALL")) {
            price = BigDecimal.valueOf(9.90);
        } else if (dimension.equals("MEDIUM")) {
            price = BigDecimal.valueOf(16.90);
        } else if (dimension.equals("LARGE")) {
            price = BigDecimal.valueOf(28.90);
        }

        order.setPrice(price);

        DataSource.addOrder(order);

        System.out.println("Send order to: " + recipient.getName());
    }

    @FXML
    void initialize() {
        webViewConnector = new WebViewConnector();
        setupWebView(orderWebView, "customer_map.html");
        setupWebView(trackWebView, "customer_map.html");

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
        });

        webViewConnector.sendPaczkamatProperty().addListener((observableValue, oldPaczkamat, newPaczkamat) -> {
            sendPaczkamat = newPaczkamat;
            sendPaczkamatName.setText(newPaczkamat.getName());
        });

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
