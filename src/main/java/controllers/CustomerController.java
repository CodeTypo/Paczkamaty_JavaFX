package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
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
    private ComboBox<?> recipientComboBox;

    @FXML
    private ComboBox<?> paczkamatComboBox;

    @FXML
    private ComboBox<?> dimensionComboBox;

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

    @FXML
    void initialize() {
        webViewConnector = new WebViewConnector();
        setupWebView(orderWebView, "customer_map.html");
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
