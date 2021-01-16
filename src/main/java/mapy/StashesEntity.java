package mapy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stashes", schema = "paczkamatDB", catalog = "")
public class StashesEntity {
    private String id;
    private Object dimension;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dimension")
    public Object getDimension() {
        return dimension;
    }

    public void setDimension(Object dimension) {
        this.dimension = dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StashesEntity that = (StashesEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(dimension, that.dimension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dimension);
    }
}
