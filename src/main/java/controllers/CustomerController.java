package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Customer;
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
    private CheckBox expressCheckbox;

    @FXML
    private Button orderBtn;

    @FXML
    private Text textMsg;

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

    @FXML
    void onLogoutClicked(ActionEvent event) {
        SessionStore.setLoggedIn(false);
        showNewlayout("layout/login_screen.fxml", event);
    }

    @FXML
    void onOrderClicked(ActionEvent event) {
        Customer recipient = recipientComboBox.getValue();
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
