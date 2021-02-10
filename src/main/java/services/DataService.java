package services;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;

import java.util.List;

/**
 * Interfejs dzielony zarówno przez online jak i offline źródła danych, mający zapewnić integralność źródeł z programem
 * niezależnie od tego, czy są one online'ową bazą danych, czy też offline'owym, zhardcode'owanym źródłem jakim jest mockDB.
 */
//An interface shard by both the online and offline data sources
interface DataService {
    List<Paczkamat> getAllPaczkamats();
    List<Order> getAllOrders();
    List<Customer> getAllCustomers();
    List<Stash> getAllStashes();

    Customer getLoggedInUser(String login, String password) throws Exception;

    void insertCustomer  ( Customer customer );
    void insertOrder     ( Order order );
    void insertStash     ( Stash stash );
    void insertPaczkamat ( Paczkamat paczkamat );

    <T> void updateEntity(T entity);
}
