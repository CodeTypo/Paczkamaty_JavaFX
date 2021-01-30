package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
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
    private GridPane paczkamatStashesGrid;

    @FXML
    private Tab ordersTab;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private GridPane orderDetailsGrid;

    @FXML
    private Tab customersTab;

    @FXML
    private TableView<?> customersTable;

    @FXML
    private LineChart<?, ?> customersLineChart;

    @FXML
    private BarChart<?, ?> customersBarChart;

    @FXML
    private Button logoutBtn;

    private WebViewConnector webViewConnector;

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
                        System.out.println("Tab Selection changed to" + t1.getText());
                    }
                }
        );

        ordersTable.setItems(DataSource.getOrders());

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
