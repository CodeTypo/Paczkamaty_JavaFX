package services;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;

import java.util.List;

interface DataService {
    List<Paczkamat> getAllPaczkamats();
    List<Order> getAllOrders();
    List<Customer> getAllCustomers();
    List<Stash> getAllStashes();

    Customer getLoggedInUser(String login, String password);

    <T> void insertEntity(T entity);
    <T> void updateEntity(T entity);
}
