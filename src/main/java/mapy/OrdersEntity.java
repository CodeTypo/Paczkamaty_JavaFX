package mapy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "paczkamatDB", catalog = "")
public class OrdersEntity {
    private String id;
    private BigDecimal price;
    private Object orderStatus;
    private Timestamp sendDatetime;
    private Timestamp receiveDatetime;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public Object getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Object orderStatus) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(price, that.price) && Objects.equals(orderStatus, that.orderStatus) && Objects.equals(sendDatetime, that.sendDatetime) && Objects.equals(receiveDatetime, that.receiveDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, orderStatus, sendDatetime, receiveDatetime);
    }
}
