import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private ObservableList<Paczkamat> paczkamats = FXCollections.observableArrayList();
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private ObservableList<Stash> stashes = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    private Customer loggedUser;

    private PaczkamatService service;

    public DataRepository(PaczkamatService service) {
        this.service = service;
        fetchCustomers();
        fetchStashes();
        fetchPaczkamats();
        fetchOrders();
    }

    public void updateOrder(Order order) {
        service.updateEntity(order);
    }

    public void updateCustomer(Customer customer) {
        service.updateEntity(customer);
    }

    public void updatePaczkamat(Paczkamat paczkamat) {
        service.updateEntity(paczkamat);
    }

    public void updateStash(Stash stash) {
        service.updateEntity(stash);
    }

    public void fetchPaczkamats() {
        paczkamats = FXCollections.observableArrayList(service.getAllPaczkamats());
    }

    public void fetchCustomers() {
        customers = FXCollections.observableArrayList(service.getAllCustomers());
    }

    public void fetchOrders() {
        orders = FXCollections.observableArrayList(service.getAllOrders());
    }

    public void fetchStashes() {
        stashes = FXCollections.observableArrayList(service.getAllStashes());
    }

    public void addPaczkamat(Paczkamat paczkamat){
        paczkamats.add(paczkamat);
        service.insertEntity(paczkamat);
    }

    public void addOrder(Order order){
        orders.add(order);
        service.insertEntity(order);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
        service.insertEntity(customer);
    }

    public void addStash(Stash stash){
        stashes.add(stash);
        service.insertEntity(stash);
    }

    public ObservableList<Paczkamat> getPaczkamats() {
        return paczkamats;
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }

    public ObservableList<Stash> getStashes() {
        return stashes;
    }

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public Customer getLoggedUser(String login, String password) {
        if (loggedUser == null) {
            loggedUser = service.getLoggedInUser(login, password);
        }
        return loggedUser;
    }
}
