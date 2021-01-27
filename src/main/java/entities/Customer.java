package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="customers")
public class Customer {
    private Integer id;
    private String email;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String login;
    private String password;
    private Collection<Order> ordersAsSender;
    private Collection<Order> ordersAsReceiver;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(email, customer.email) && Objects.equals(name, customer.name) && Objects.equals(lastName, customer.lastName) && Objects.equals(phoneNumber, customer.phoneNumber) && Objects.equals(login, customer.login) && Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, lastName, phoneNumber, login, password);
    }

    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
    public Collection<Order> getOrdersAsSender() {
        return ordersAsSender;
    }

    public void setOrdersAsSender(Collection<Order> ordersAsSender) {
        this.ordersAsSender = ordersAsSender;
    }

    @OneToMany(mappedBy = "receiver", fetch = FetchType.EAGER)
    public Collection<Order> getOrdersAsReceiver() {
        return ordersAsReceiver;
    }

    public void setOrdersAsReceiver(Collection<Order> ordersAsReceiver) {
        this.ordersAsReceiver = ordersAsReceiver;
    }
}
