package services;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;

import java.util.ArrayList;
import java.util.List;

public class MockService implements DataService {
    private List<Paczkamat> paczkamats;
    private List<Order> orders;
    private List<Customer> customers;
    private List<Stash> stashes;

    public MockService() {
        this.paczkamats = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.stashes = new ArrayList<>();

        mockCustomers();
    }

    private void mockCustomers() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setLogin("adam");
        customer.setPassword("malysz");
        customer.setName("Adam");
        customer.setLastName("Malysz");
        customer.setEmail("adam@gmail.com");
        customer.setPhoneNumber("123456789");

        customers.add(customer);

        customer = new Customer();
        customer.setId(2);
        customer.setLogin("tomek");
        customer.setPassword("kowalski");
        customer.setName("Tomasz");
        customer.setLastName("Kowalski");
        customer.setEmail("tomasz@gmail.com");
        customer.setPhoneNumber("123456789");

        customers.add(customer);

        customer = new Customer();
        customer.setId(3);
        customer.setLogin("jan");
        customer.setPassword("nowak");
        customer.setName("Jan");
        customer.setLastName("Nowak");
        customer.setEmail("jan@gmail.com");
        customer.setPhoneNumber("123456789");

        customers.add(customer);
    }

    @Override
    public List<Paczkamat> getAllPaczkamats() {
        return paczkamats;
    }

    @Override
    public List<Order> getAllOrders() {
        return orders;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public List<Stash> getAllStashes() {
        return stashes;
    }

    @Override
    public Customer getLoggedInUser(String login, String password) throws Exception {
        Customer loggedUser = null;
        for (Customer customer: customers) {
            if (customer.getLogin().equals(login) && customer.getPassword().equals(password)) {
                loggedUser = customer;
            }
        }
        if (loggedUser == null) {
            throw new Exception("Invalid login and password. Search for existing user in MockService");
        }
        return loggedUser;
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void insertOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void insertStash(Stash stash) {
        stashes.add(stash);
    }

    @Override
    public void insertPaczkamat(Paczkamat paczkamat) {
        paczkamats.add(paczkamat);
    }

//    @Override
//    public <T> void insertEntity(T entity) {
//    }

    @Override
    public <T> void updateEntity(T entity) {
        // do nothing hence we do not have backend to update
        // in mock service
    }
}
