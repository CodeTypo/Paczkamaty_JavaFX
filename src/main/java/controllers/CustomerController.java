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
import javafx.scene.web.WebView;

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
    private WebView selectWebView;

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
    private WebView ordersWebView;

    @FXML
    private ListView<?> ordersList;

    @FXML
    void initialize() {


    }
}
