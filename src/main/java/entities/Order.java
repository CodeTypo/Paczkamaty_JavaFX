package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Klasa Order, do której przy pomocy Hibernate mapowana jest tabela orders z bazy danych.
 * Reprezentuje pojedyncze zamówienie - zlecenie przesyłki / odbioru.
 * Zawiera podstawowe metody typu get / set / equals / toString
 */
@Entity
@Table(name="orders")
public class Order {
    private Integer id;
    private BigDecimal price;
    private String orderStatus;
    private Timestamp sendDatetime;
    private Timestamp receiveDatetime;
    private Customer sender;
    private Stash senderStash;
    private Customer receiver;
    private Stash receiverStash;

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
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "order_status")
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Basic
    @Column(name = "send_datetime")
    public Timestamp getSendDatetime() {
        return sendDatetime;
    }

    public void setSendDatetime(Timestamp sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

    @Basic
    @Column(name = "receive_datetime")
    public Timestamp getReceiveDatetime() {
        return receiveDatetime;
    }

    public void setReceiveDatetime(Timestamp receiveDatetime) {
        this.receiveDatetime = receiveDatetime;
    }

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "sender_stash_id", referencedColumnName = "id")
    public Stash getSenderStash() {
        return senderStash;
    }

    public void setSenderStash(Stash senderStash) {
        this.senderStash = senderStash;
    }

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    @ManyToOne
    @JoinColumn(name = "receiver_stash_id", referencedColumnName = "id")
    public Stash getReceiverStash() {
        return receiverStash;
    }

    public void setReceiverStash(Stash receiverStash) {
        this.receiverStash = receiverStash;
    }
}
