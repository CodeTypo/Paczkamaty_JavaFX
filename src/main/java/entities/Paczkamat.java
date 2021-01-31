package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="paczkamats")
public class Paczkamat {
    private String name;
    private String buildingNumber;
    private String city;
    private String postCode;
    private String province;
    private String street;
    private String latitude;
    private String longitude;
    private String openingHours;
    private Collection<Stash> stashes = new ArrayList<>();

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "building_number")
    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "post_code")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "latitude")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "opening_hours")
    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paczkamat that = (Paczkamat) o;
        return Objects.equals(name, that.name) && Objects.equals(buildingNumber, that.buildingNumber) && Objects.equals(city, that.city) && Objects.equals(postCode, that.postCode) && Objects.equals(province, that.province) && Objects.equals(street, that.street) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(openingHours, that.openingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buildingNumber, city, postCode, province, street, latitude, longitude, openingHours);
    }

    @OneToMany(mappedBy = "paczkamat", fetch = FetchType.EAGER)
    public Collection<Stash> getStashes() {
        return stashes;
    }

    public void setStashes(Collection<Stash> stashes) {
        this.stashes = stashes;
    }
}
