package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="stashes")
public class Stash {
    private Integer id;
//    private String paczkamatId;
    private String dimension;
    private Collection<Order> ordersToSend;
    private Collection<Order> ordersToReceive;
    private Paczkamat paczkamat;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    @Basic
//    @Column(name = "paczkamat_id")
//    public String getPaczkamatId() {
//        return paczkamatId;
//    }
//
//    public void setPaczkamatId(String paczkamatId) {
//        this.paczkamatId = paczkamatId;
//    }

    @Basic
    @Column(name = "dimension")
    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Stash stash = (Stash) o;
//        return Objects.equals(id, stash.id) && Objects.equals(paczkamatId, stash.paczkamatId) && Objects.equals(dimension, stash.dimension);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, paczkamatId, dimension);
//    }

    @OneToMany(mappedBy = "senderStash")
    public Collection<Order> getOrdersToSend() {
        return ordersToSend;
    }

    public void setOrdersToSend(Collection<Order> ordersToSend) {
        this.ordersToSend = ordersToSend;
    }

    @OneToMany(mappedBy = "receiverStash")
    public Collection<Order> getOrdersToReceive() {
        return ordersToReceive;
    }

    public void setOrdersToReceive(Collection<Order> ordersToReceive) {
        this.ordersToReceive = ordersToReceive;
    }

    @ManyToOne
    @JoinColumn(name = "paczkamat_id", referencedColumnName = "name")
    public Paczkamat getPaczkamat() {
        return paczkamat;
    }

    public void setPaczkamat(Paczkamat paczkamat) {
        this.paczkamat = paczkamat;
    }
}
