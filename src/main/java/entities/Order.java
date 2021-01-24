package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name="orders")
public class Order {
    private Integer id;
    private BigDecimal price;
    private String orderStatus;
//    private Integer senderId;
//    private Integer senderStashId;
    private Timestamp sendDatetime;
//    private Integer receiverId;
//    private Integer receiverStashId;
    private Timestamp receiveDatetime;
    private Customer sender;
    private Stash senderStash;
    private Customer receiver;
    private Stash receiverStash;

    @Id
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

//    @Basic
//    @Column(name = "sender_id")
//    public Integer getSenderId() {
//        return senderId;
//    }
//
//    public void setSenderId(Integer senderId) {
//        this.senderId = senderId;
//    }
//
//    @Basic
//    @Column(name = "sender_stash_id")
//    public Integer getSenderStashId() {
//        return senderStashId;
//    }
//
//    public void setSenderStashId(Integer senderStashId) {
//        this.senderStashId = senderStashId;
//    }

    @Basic
    @Column(name = "send_datetime")
    public Timestamp getSendDatetime() {
        return sendDatetime;
    }

    public void setSendDatetime(Timestamp sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

//    @Basic
//    @Column(name = "receiver_id")
//    public Integer getReceiverId() {
//        return receiverId;
//    }
//
//    public void setReceiverId(Integer receiverId) {
//        this.receiverId = receiverId;
//    }
//
//    @Basic
//    @Column(name = "receiver_stash_id")
//    public Integer getReceiverStashId() {
//        return receiverStashId;
//    }
//
//    public void setReceiverStashId(Integer receiverStashId) {
//        this.receiverStashId = receiverStashId;
//    }

    @Basic
    @Column(name = "receive_datetime")
    public Timestamp getReceiveDatetime() {
        return receiveDatetime;
    }

    public void setReceiveDatetime(Timestamp receiveDatetime) {
        this.receiveDatetime = receiveDatetime;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Order order = (Order) o;
//        return Objects.equals(id, order.id) && Objects.equals(price, order.price) && Objects.equals(orderStatus, order.orderStatus) && Objects.equals(senderId, order.senderId) && Objects.equals(senderStashId, order.senderStashId) && Objects.equals(sendDatetime, order.sendDatetime) && Objects.equals(receiverId, order.receiverId) && Objects.equals(receiverStashId, order.receiverStashId) && Objects.equals(receiveDatetime, order.receiveDatetime);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, price, orderStatus, senderId, senderStashId, sendDatetime, receiverId, receiverStashId, receiveDatetime);
//    }

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
