package controllers;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import services.DataSource;
import services.SessionStore;
import web.WebViewConnector;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Klasa AdminController odpowiedzialna za obsługę wszystkich interakcji pomiędzy użytkownikiem
 * zalogowanym jako administrator, a oddzielnym GUI przygotowanym dla tego użykownika.
 */
public class AdminController {


    //Szereg pól odpowiadających FXML-owym obiektom zastosowanym w GUI
    @FXML
    private TabPane tabPane;

    @FXML
    private WebView paczkamatsWebView;

    @FXML
    private TableView<Order> sentOrdersTable;

    @FXML
    private TableView<Order> paczkamatStatsTable;

    @FXML
    private DatePicker datePickerPaczkamats;

    @FXML
    private DatePicker datePickerOrders;

    @FXML
    private TableView<Order> receivedOrdersTable;

    @FXML
    private Label incomeInfo;

    @FXML
    private Text SenderTextID;

    @FXML
    private Text SenderTextName;

    @FXML
    private Text SenderTextEmail;

    @FXML
    private Text SenderTextLastName;

    @FXML
    private Text SenderTextPassword;

    @FXML
    private Text SenderTextLogin;

    @FXML
    private Text SenderTextPhone;

    @FXML
    private Text ReceiverTextID;

    @FXML
    private Text ReceiverTextPassword;

    @FXML
    private Text ReceiverTextLogin;

    @FXML
    private Text ReceiverTextPhone;

    @FXML
    private Text ReceiverTextEmail;

    @FXML
    private Text ReceiverTextLastName;

    @FXML
    private Text ReceiverTextName;

    @FXML
    private Text PaczkamatSenderTextName;

    @FXML
    private Text PaczkamatSenderTextBuilding;

    @FXML
    private Text PaczkamatSenderTextCity;

    @FXML
    private Text PaczkamatSenderTextPostal;

    @FXML
    private Text PaczkamatSenderTextProvince;

    @FXML
    private Text PaczkamatSenderTextStreet;

    @FXML
    private Text PaczkamatSenderTextLatitude;

    @FXML
    private Text PaczkamatSenderTextLongitude;

    @FXML
    private Text PaczkamatSenderTextOpening;

    @FXML
    private Text PaczkamatRecipientTextName;

    @FXML
    private Text PaczkamatRecipientTextBuilding;

    @FXML
    private Text PaczkamatRecipientTextCity;

    @FXML
    private Text PaczkamatRecipientTextPostal;

    @FXML
    private Text PaczkamatRecipientTextProvince;

    @FXML
    private Text PaczkamatRecipientTextStrret;

    @FXML
    private Text PaczkamatRecipientTextLatitude;

    @FXML
    private Text PaczkamatRecipientTextLongitude;

    @FXML
    private Text PaczkamatRecipientTextOpeninig;
    //Koniec Szeregu pól odpowiadającym FXML-owym obiektom zastosowanym w GUI

    private WebViewConnector    webViewConnector;
    private Paczkamat           adminPaczkamat = null;
    private Order               selectedOrder = null;
    private BigDecimal          income = BigDecimal.ZERO;

    /**
     * @param event
     * Metoda wykonująca się w momencie gdy użytkownik naciska na przycisk LogOut. Odpowiada
     * za wylogowanie z sessji obecnego użytkownika oraz wyświetlenie nowego layoutu ukazującego
     * ekran logowania.
     */
    @FXML
    void onLogoutClicked(ActionEvent event) {
        SessionStore.setLoggedIn(false);
        showNewlayout("layout/login_screen.fxml", event);
    }

    /**
     * Metoda wywoływana na początku "istnienia" Controllera, jej zadaniami są:
     *  - przygotowanie WebView poprzez utworzenie nowego obiektu WebViewConnector, któremu następnie przekazywane są
     *    zmienna odpowiadająca FXML-owemu obiektowi klasy WebView oraz plik .html, który zawiera kod, który wykona się w WebView
     *  - dodanie listenera do obiektu klasy TabPane reagującego na wybranie innej zakładki przez użytkownika
     *  - zaimplementowanie możliwości sortowania zamówień przy pomocy daty, oraz metody zliczającej przychód uzyskany
     *    wybranego dnia.
     *  - dodanie do zamówień zestawionych w tabelach listenerów, powodujących wyświetlenie w przygotowanym do tego polu
     *    szczegółowych danych na temat adresata, nadawcy oraz paczkamatów nadawczego i odbiorczego.
     */
    @FXML
    void initialize() {
        webViewConnector = new WebViewConnector();
        setupWebView(paczkamatsWebView, "admin_map.html");

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                (ov, oldTab, newTab) -> System.out.println("Tab Selection changed to " + newTab.getText())
        );

        webViewConnector.adminSetPaczkamatProperty().addListener((observableValue, oldPaczkamat, newPaczkamat) -> {
            adminPaczkamat = newPaczkamat;
            System.out.println(adminPaczkamat.getName());
            paczkamatStatsTable.setItems(DataSource.getOrders().filtered(order ->
                    order.getSenderStash().getPaczkamat().getName().equals(adminPaczkamat.getName())  ||
                    order.getReceiverStash().getPaczkamat().getName().equals(adminPaczkamat.getName())
            ));

        });

        datePickerPaczkamats.setOnAction(actionEvent -> {
            LocalDate date = datePickerPaczkamats.getValue();
            paczkamatStatsTable.setItems(DataSource.getOrders().filtered(order -> {
                LocalDateTime sendTime = order.getSendDatetime().toLocalDateTime();
                // display only orders for this paczkamat and given day
                if (adminPaczkamat == null) {
                    // display only orders for this paczkamat and given day
                    return sendTime.getYear() == date.getYear() &&
                            sendTime.getMonthValue() == date.getMonthValue() &&
                            sendTime.getDayOfMonth() == date.getDayOfMonth();
                } else return (
                        (order.getSenderStash().getPaczkamat().getName().equals(adminPaczkamat.getName()) && order.getOrderStatus().equals("AWAITING_PICKUP"))
                                || (order.getReceiverStash().getPaczkamat().getName().equals(adminPaczkamat.getName()) && order.getOrderStatus().equals("IN_SHIPMENT"))
                ) &&
                        sendTime.getYear() == date.getYear() &&
                        sendTime.getMonthValue() == date.getMonthValue() &&
                        sendTime.getDayOfMonth() == date.getDayOfMonth();
            }));
        });

        datePickerOrders.setOnAction(actionEvent -> {

            LocalDate date = datePickerOrders.getValue();
            income = BigDecimal.ZERO;

            sentOrdersTable.setItems(DataSource.getOrders().filtered(order -> {
                LocalDateTime sendTime = order.getSendDatetime().toLocalDateTime();
                if (
                        (order.getOrderStatus().equals("AWAITING_PICKUP") ||
                                order.getOrderStatus().equals("IN_DELIVERY") ||
                                order.getOrderStatus().equals("IN_SHIPMENT")) &&
                        sendTime.getYear() == date.getYear() &&
                        sendTime.getMonthValue() == date.getMonthValue() &&
                        sendTime.getDayOfMonth() == date.getDayOfMonth()
                ) {
                    income = income.add(order.getPrice());
                    // display only orders for this paczkamat and given day
                    return true;
                }
                return false;
            }));

            receivedOrdersTable.setItems(DataSource.getOrders().filtered(order -> {
                LocalDateTime sendTime = order.getSendDatetime().toLocalDateTime();
                if (
                        order.getOrderStatus().equals("REALIZED") &&
                        sendTime.getYear() == date.getYear() &&
                        sendTime.getMonthValue() == date.getMonthValue() &&
                        sendTime.getDayOfMonth() == date.getDayOfMonth()
                ) {
                    // display only orders for this paczkamat and given day
                    income = income.add(order.getPrice());
                    return true;
                }

                return false;
            }));

            incomeInfo.setText("Income: " + income.toString());
        });

        paczkamatStatsTable.setItems(DataSource.getOrders().filtered(order -> order.getOrderStatus().equals("AWAITING_PICKUP")));

        sentOrdersTable.setItems(DataSource.getOrders().filtered(order -> order.getOrderStatus().equals("AWAITING_PICKUP") || order.getOrderStatus().equals("IN_DELIVERY")));

        sentOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, order, newOrder) -> {
            if (newOrder != null) {
                System.out.println("Selected order: " + newOrder.getId());
                selectedOrder = newOrder;
                printDetailedInfo(newOrder);
            }
        });

        receivedOrdersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, order, newOrder) -> {
            if (newOrder != null) {
                System.out.println("Selected order: " + newOrder.getId());
                printDetailedInfo(newOrder);
            }
        });

        receivedOrdersTable.setItems(DataSource.getOrders().filtered(order -> order.getOrderStatus().equals("IN_SHIPMENT") || order.getOrderStatus().equals("REALIZED") ));

    }

    /**
     * @param order Obiekt klasy zamówienie, którego szczegóły mają być wyświetlone w przygotowanym w tym celu polu w GUI
     *
     * Metoda ta powoduje wyświetlenie w przygotowanym do tego polu
     * szczegółowych danych na temat adresata, nadawcy oraz paczkamatów nadawczego i odbiorczego powiązanych z konkretnym zamówieniem.
     */
    private void printDetailedInfo(Order order) {
        Customer sender = order.getSender();
        Customer recipient = order.getReceiver();
        Paczkamat senderPaczkamat = order.getSenderStash().getPaczkamat();
        Paczkamat recipientPaczkamat = order.getReceiverStash().getPaczkamat();

        //senderLabel.setText("Sender: \n" + sender.toStringCustom());
        SenderTextID.setText(String.valueOf(sender.getId()));
        SenderTextName.setText(sender.getName());
        SenderTextLastName.setText(sender.getLastName());
        SenderTextEmail.setText(sender.getEmail());
        SenderTextPhone.setText(sender.getPhoneNumber());
        SenderTextLogin.setText(sender.getLogin());
        SenderTextPassword.setText(sender.getPassword());

        //recipientLabel.setText("Recipient: \n" + recipient.toStringCustom());

        ReceiverTextID.setText(String.valueOf(recipient.getId()));
        ReceiverTextName.setText(recipient.getName());
        ReceiverTextLastName.setText(recipient.getLastName());
        ReceiverTextEmail.setText(recipient.getEmail());
        ReceiverTextPhone.setText(recipient.getPhoneNumber());
        ReceiverTextLogin.setText(recipient.getLogin());
        ReceiverTextPassword.setText(recipient.getPassword());

        //senderPaczkamatLabel.setText("Sender paczkamat: \n" + senderPaczkamat.toString());
        PaczkamatSenderTextName.setText(senderPaczkamat.getName());
        PaczkamatSenderTextBuilding.setText(senderPaczkamat.getBuildingNumber());
        PaczkamatSenderTextCity.setText(senderPaczkamat.getCity());
        PaczkamatSenderTextPostal.setText(senderPaczkamat.getPostCode());
        PaczkamatSenderTextProvince.setText(senderPaczkamat.getProvince());
        PaczkamatSenderTextStreet.setText(senderPaczkamat.getStreet());
        PaczkamatSenderTextLatitude.setText(senderPaczkamat.getLatitude());
        PaczkamatSenderTextLongitude.setText(senderPaczkamat.getLongitude());
        PaczkamatSenderTextOpening.setText(senderPaczkamat.getOpeningHours());

        //recipientPaczkamatLabel.setText("Recipient paczkamat: \n" + recipientPaczkamat.toString());
        PaczkamatRecipientTextName.setText(recipientPaczkamat.getName());
        PaczkamatRecipientTextBuilding.setText(recipientPaczkamat.getBuildingNumber());
        PaczkamatRecipientTextCity.setText(recipientPaczkamat.getCity());
        PaczkamatRecipientTextPostal.setText(recipientPaczkamat.getPostCode());
        PaczkamatRecipientTextProvince.setText(recipientPaczkamat.getProvince());
        PaczkamatRecipientTextStrret.setText(recipientPaczkamat.getStreet());
        PaczkamatRecipientTextLatitude.setText(recipientPaczkamat.getLatitude());
        PaczkamatRecipientTextLongitude.setText(recipientPaczkamat.getLongitude());
        PaczkamatRecipientTextOpeninig.setText(recipientPaczkamat.getOpeningHours());

    }


    /**
     * Metoda pozwalająca administratorowi ręcznie zmienić status wybranej przez niego przesyłki na "IN_DELIVERY"
     */
    @FXML
    void setDeliveryStatus() {
        selectedOrder.setOrderStatus("IN_DELIVERY");
        DataSource.updateOrder(selectedOrder);
        sentOrdersTable.refresh();
        receivedOrdersTable.refresh();

    }

    /**
     * Metoda pozwalająca administratorowi ręcznie zmienić status wybranej przez niego przesyłki na "IN_SHIPMENT"
     */
    @FXML
    void setShipmentStatus() {
        selectedOrder.setOrderStatus("IN_SHIPMENT");
        DataSource.updateOrder(selectedOrder);
        sentOrdersTable.refresh();
        receivedOrdersTable.refresh();
    }

    /**
     * Metoda pozwalająca administratorowi ręcznie zmienić status wybranej przez niego przesyłki na "REALIZED"
     */
    @FXML
    void setRealizedStatus() {
        selectedOrder.setOrderStatus("REALIZED");
        selectedOrder.setReceiveDatetime(Timestamp.from(Instant.now()));
        DataSource.updateOrder(selectedOrder);
        sentOrdersTable.refresh();
        receivedOrdersTable.refresh();
    }

    /**
     * @param webView FXML - owy obiekt klasy WebView, w którym chcemy uruchomić kod HTML
     * @param htmlFile nazwa pliku HTML który chcemy wczytać do WebView
     *
     * Metoda ta wczytuje plik HTML do obiektu klasy WebView dodatkowo implementując Listener, który pozwala na
     * zareagowanie w momencie gdy użytkownik wybierze paczkamat.
     */
    void setupWebView(WebView webView, String htmlFile) {
        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("app", webViewConnector);
                });

        webEngine.load(getClass().getResource("/webview/" + htmlFile).toString());
    }

    /**
     * @param path Ścieżka do pliku .fxml zawierającego layout do nowego okna, które chcemy pokazać
     * @param event obiekt klasy ActionEvent, dzięki któremu mamy możliwość ukrycia layoutu, w którym znajdował się przycisk
     * który wywołał tą metodę.
     */
    private void showNewlayout(String path, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
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
