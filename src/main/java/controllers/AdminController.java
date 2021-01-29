package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
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
    private ListView<?> paczkamatsListView;

    @FXML
    private WebView paczkamatsWebView;

    @FXML
    private GridPane paczkamatStashesGrid;

    @FXML
    private Tab ordersTab;

    @FXML
    private TableView<?> ordersTable;

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

    private WebViewConnector webViewConnector;

    @FXML
    void initialize() {
        webViewConnector = new WebViewConnector();
        setupWebView(paczkamatsWebView, "admin_map.html");
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
