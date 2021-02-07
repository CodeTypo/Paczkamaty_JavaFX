package services;

import entities.Customer;
import entities.Order;
import entities.Paczkamat;
import entities.Stash;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//An offline data source made for the purpose of debugging the app without the need of connecting to AWS
public class    MockService implements DataService {
    private List<Paczkamat> paczkamats;
    private List<Order>     orders;
    private List<Customer>  customers;
    private List<Stash>     stashes;

    public MockService() {
        this.paczkamats = new ArrayList<>();
        this.orders     = new ArrayList<>();
        this.customers  = new ArrayList<>();
        this.stashes    = new ArrayList<>();
        mockCustomers();
        mockPaczkamatsAndStashes();
        mockOrders();
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

        for (int i = 0; i < 20; i++) {
            customer = new Customer();
            customer.setId(i+4);
            customer.setLogin(Integer.toString(i));
            customer.setPassword(Integer.toString(i));
            customer.setName("" + i);
            customer.setLastName("" + i);
            customer.setEmail("" + i + "@gmail.com");
            customer.setPhoneNumber("123456789");

            customers.add(customer);
        }
    }

    private void mockOrders() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                Order order = new Order();
                order.setSenderStash(stashes.get(i*10+j));
                order.setReceiverStash(stashes.get((i+1)*10+j));
                if (j%2 == 0) {
                    order.setOrderStatus("AWAITING_PICKUP");
                } else {
                    order.setOrderStatus("REALIZED");
                }

                order.setSender(customers.get(i));
                order.setReceiver(customers.get(i+1));
                order.setSendDatetime(Timestamp.from(Instant.now()));
                order.setPrice(BigDecimal.TEN);
                order.setReceiveDatetime(Timestamp.from(Instant.now().plusSeconds(100)));
                order.setId(i+j*20);

                orders.add(order);

                customers.get(i).getOrdersAsSender().add(order);
                customers.get(i+1).getOrdersAsReceiver().add(order);
            }
        }

    }

    private void mockPaczkamatsAndStashes() {
        for (int j = 0; j < 10; j++) {

            Paczkamat paczkamat = new Paczkamat();
            paczkamat.setName("WAW-123A" + j);
            paczkamat.setStreet("jsahdja");
            paczkamat.setPostCode("12-234");
            paczkamat.setProvince("asdada");
            paczkamat.setLongitude("123.23232");
            paczkamat.setLatitude("123.23232");
            paczkamat.setCity("Wroclaw");
            paczkamat.setOpeningHours("24/h");
            paczkamat.setBuildingNumber("12a");

            Collection<Stash> newStashes = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                Stash stash = new Stash();
                if (i%3 == 0){
                    stash.setDimension("SMALL");
                } else if (i%3 == 1) {
                    stash.setDimension("MEDIUM");
                } else {
                    stash.setDimension("LARGE");
                }
                stash.setPaczkamat(paczkamat);
                stash.setId(i + j*15);
                stashes.add(stash);
                newStashes.add(stash);
            }

            paczkamat.setStashes(newStashes);

            paczkamats.add(paczkamat);
        }

    }

    @Override
    public List<Paczkamat> getAllPaczkamats() {
        return paczkamats;
    }

    @Override
    public List<Order> getAllOrders()       {
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
        System.out.println(loggedUser.getName());
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
