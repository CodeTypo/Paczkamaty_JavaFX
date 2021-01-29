package services;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;

public class DataSource {
    private static ObservableList<Paczkamat> paczkamats = FXCollections.observableArrayList();
    private static ObservableList<Order> orders = FXCollections.observableArrayList();
    private static ObservableList<Stash> stashes = FXCollections.observableArrayList();
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();

    private static Customer loggedUser;

    private static DataService service;
//
//    public DataRepository(services.PaczkamatService service) {
//        this.service = service;
//        fetchCustomers();
//        fetchStashes();
//        fetchPaczkamats();
//        fetchOrders();
//    }

    private DataSource() {

    }

    public static void setDBService(String username, String password) {
        service = new DBService(username, password);
        System.out.println("Hibernate database connector set");
    }

    public static void setMockService() {
        service = new MockService();
        System.out.println("Mock service will be used");
    }

    public static void updateOrder(Order order) {
        service.updateEntity(order);
    }

    public static void updateCustomer(Customer customer) {
        service.updateEntity(customer);
    }

    public static void updatePaczkamat(Paczkamat paczkamat) {
        service.updateEntity(paczkamat);
    }

    public static void updateStash(Stash stash) {
        service.updateEntity(stash);
    }

    public static void fetchPaczkamats() {
        paczkamats = FXCollections.observableArrayList(service.getAllPaczkamats());
    }

    public static void fetchCustomers() {
        customers = FXCollections.observableArrayList(service.getAllCustomers());
    }

    public static void fetchOrders() {
        orders = FXCollections.observableArrayList(service.getAllOrders());
    }

    public static void fetchStashes() {
        stashes = FXCollections.observableArrayList(service.getAllStashes());
    }

    public static void addPaczkamat(Paczkamat paczkamat){
        paczkamats.add(paczkamat);
        service.insertPaczkamat(paczkamat);
    }

    public static void addOrder(Order order){
        orders.add(order);
        service.insertOrder(order);
    }

    public static void addCustomer(Customer customer){
        customers.add(customer);
        service.insertCustomer(customer);
    }

    public static void addStash(Stash stash){
        stashes.add(stash);
        service.insertStash(stash);
    }

    public static ObservableList<Paczkamat> getPaczkamats() {
        return paczkamats;
    }

    public static ObservableList<Order> getOrders() {
        return orders;
    }

    public static ObservableList<Stash> getStashes() {
        return stashes;
    }

    public static ObservableList<Customer> getCustomers() {
        return customers;
    }

    public static Customer getLoggedUser(String login, String password) throws Exception {
        if (loggedUser == null) {
            try {
                loggedUser = service.getLoggedInUser(login, password);
            } catch (Exception e) {
                throw e;
            }
        }

        return loggedUser;
    }
}
