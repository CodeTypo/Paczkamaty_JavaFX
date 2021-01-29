package services;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;

import java.util.ArrayList;
import java.util.List;

public class MockService implements DataService {

    @Override
    public List<Paczkamat> getAllPaczkamats() {
        List<Paczkamat> paczkamats =new ArrayList<>();

        return paczkamats;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders =new ArrayList<>();
        return orders;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers =new ArrayList<>();
        return customers;
    }

    @Override
    public List<Stash> getAllStashes() {
        List<Stash> stashes = new ArrayList<>();
        return stashes;
    }

    @Override
    public Customer getLoggedInUser(String login, String password) {
        Customer loggedUser = new Customer();
        loggedUser.setLogin(login);
        loggedUser.setPassword(password);
        loggedUser.setName("Tomasz");
        loggedUser.setLastName("Kowalski");
        loggedUser.setEmail("tomasz@gmail.com");
        loggedUser.setPhoneNumber("123456789");

        return loggedUser;
    }

    @Override
    public <T> void insertEntity(T entity) {

    }

    @Override
    public <T> void updateEntity(T entity) {

    }
}
