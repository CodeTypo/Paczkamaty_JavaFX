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
import javafx.scene.web.WebView;

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

    @FXML
    void initialize() {


    }
}
