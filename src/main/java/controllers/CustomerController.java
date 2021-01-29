package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import netscape.javascript.JSObject;
import services.DataSource;
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

    private WebViewConnector webViewConnector;

    private ObservableList<String> dimensions = FXCollections.observableArrayList
            ("Small", "Medium", "Large");


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
}
