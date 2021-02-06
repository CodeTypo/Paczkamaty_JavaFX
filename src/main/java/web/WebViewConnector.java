package web;

import entities.Paczkamat;
import entities.Stash;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import netscape.javascript.JSObject;
import services.DataSource;

import java.util.ArrayList;
import java.util.Collection;

public class WebViewConnector {

    private ObjectProperty<Paczkamat> sendPaczkamat = new SimpleObjectProperty<>();
    private ObjectProperty<Paczkamat> receivePaczkamat = new SimpleObjectProperty<>();
    private ObjectProperty<Paczkamat> adminSetPaczkamat = new SimpleObjectProperty<>();
    private boolean selectSendPaczkamat = true;

    public WebViewConnector() { }

    public void log(String text)
    {
        System.out.println(text);
    }

    private Paczkamat getPaczkamatFromJS(JSObject object) {
        JSObject location = (JSObject) object.getMember("location");
        JSObject address_details = (JSObject) object.getMember("address_details");

        String name = object.getMember("name").toString();
        String buildingNumber = address_details.getMember("building_number").toString();
        String city = address_details.getMember("city").toString();
        String postCode = address_details.getMember("post_code").toString();
        String province = address_details.getMember("province").toString();
        String street = address_details.getMember("street").toString();
        String latitude = location.getMember("latitude").toString();
        String longitude = location.getMember("longitude").toString();
        String openingHours = object.getMember("opening_hours").toString();

//        System.out.println(name);
//        System.out.println(buildingNumber);
//        System.out.println(city);
//        System.out.println(postCode);
//        System.out.println(province);
//        System.out.println(street);
//        System.out.println(latitude);
//        System.out.println(longitude);
//        System.out.println(openingHours);

        Paczkamat paczkamat = new Paczkamat();
        paczkamat.setCity(city);
        paczkamat.setBuildingNumber(buildingNumber);
        paczkamat.setLatitude(latitude);
        paczkamat.setLongitude(longitude);
        paczkamat.setName(name);
        paczkamat.setOpeningHours(openingHours);
        paczkamat.setProvince(province);
        paczkamat.setPostCode(postCode);
        paczkamat.setStreet(street);

        Collection<Stash> stashes = new ArrayList<>();
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
            stashes.add(stash);
        }

        paczkamat.setStashes(stashes);

        return paczkamat;
    }

    public void addPaczkamat(JSObject object) {
        Paczkamat paczkamat = getPaczkamatFromJS(object);

        DataSource.addPaczkamat(paczkamat);
        System.out.println("Paczkamat added");
        for (Stash stash: paczkamat.getStashes()) {
            DataSource.addStash(stash);
            System.out.println("Stash added");
        }
    }

    public void selectPaczkamat(JSObject object) {
        String name = object.getMember("name").toString();
        Paczkamat choosedPaczkamat = null;
        for (Paczkamat paczkamat: DataSource.getPaczkamats()) {
            if (paczkamat.getName().equals(name)){
                choosedPaczkamat = paczkamat;
            }
        }

        if (choosedPaczkamat == null) {
            choosedPaczkamat = getPaczkamatFromJS(object);
//            DataSource.addPaczkamat(choosedPaczkamat);
            addPaczkamat(object);
            System.out.println("Paczkamat added on demand");
        }

        if (selectSendPaczkamat) {
            sendPaczkamat.set(choosedPaczkamat);
            System.out.println("selected send paczkamat");
        } else {
            receivePaczkamat.set(choosedPaczkamat);
            System.out.println("selected receive paczkamat");
        }
        selectSendPaczkamat = !selectSendPaczkamat;

    }

    public void selectAdminPaczkamat(JSObject object) {
        String name = object.getMember("name").toString();
        Paczkamat adminPaczkamat = null;
        for (Paczkamat paczkamat: DataSource.getPaczkamats()) {
            if (paczkamat.getName().equals(name)){
                adminPaczkamat = paczkamat;
            }
        }

        if (adminPaczkamat == null) {
            adminPaczkamat = getPaczkamatFromJS(object);
            DataSource.addPaczkamat(adminPaczkamat);
            System.out.println("Paczkamat added on demand");
        }

        adminSetPaczkamat.set(adminPaczkamat);
    }

    public ObjectProperty<Paczkamat> sendPaczkamatProperty() {
        return sendPaczkamat;
    }

    public ObjectProperty<Paczkamat> receivePaczkamatProperty() {
        return receivePaczkamat;
    }

    public ObjectProperty<Paczkamat> adminSetPaczkamatProperty() {
        return adminSetPaczkamat;
    }
}
