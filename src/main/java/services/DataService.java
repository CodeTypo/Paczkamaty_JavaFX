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

    Customer getLoggedInUser(String login, String password) throws Exception;

    void insertCustomer( Customer customer );
    void insertOrder( Order order );
    void insertStash( Stash stash );
    void insertPaczkamat( Paczkamat paczkamat );

//    void updateCustomer( Customer customer );
//    void updateOrder( Order order );
//    void updateStash( Stash stash );
//    void updatePaczkamat( Paczkamat paczkamat );

//    <T> void insertEntity(T entity);
    <T> void updateEntity(T entity);
}
