package web;

import entities.Paczkamat;
import entities.Stash;
import netscape.javascript.JSObject;
import services.DataSource;

import java.util.ArrayList;
import java.util.Collection;

public class WebViewConnector {
    private DataSource data;

    public WebViewConnector(DataSource data) {
        this.data = data;
    }

    public void log(String text)
    {
        System.out.println(text);
    }

    public void addPaczkamat(JSObject object) {
        JSObject location = (JSObject) object.getMember("location");
        JSObject address_details = (JSObject) object.getMember("address_details");

//            System.out.println(object.getMember("name"));
//            System.out.println(address_details.getMember("building_number"));
//            System.out.println(address_details.getMember("city"));
//            System.out.println(address_details.getMember("post_code"));
//            System.out.println(address_details.getMember("province"));
//            System.out.println(address_details.getMember("street"));
//            System.out.println(location.getMember("latitude"));
//            System.out.println(location.getMember("longitude"));
//            System.out.println(object.getMember("opening_hours"));

        String name = object.getMember("name").toString();
        String buildingNumber = address_details.getMember("building_number").toString();
        String city = address_details.getMember("city").toString();
        String postCode = address_details.getMember("post_code").toString();
        String province = address_details.getMember("province").toString();
        String street = address_details.getMember("street").toString();
        String latitude = location.getMember("latitude").toString();
        String longitude = location.getMember("longitude").toString();
        String openingHours = object.getMember("opening_hours").toString();

        System.out.println(name);
        System.out.println(buildingNumber);
        System.out.println(city);
        System.out.println(postCode);
        System.out.println(province);
        System.out.println(street);
        System.out.println(latitude);
        System.out.println(longitude);
        System.out.println(openingHours);

        System.out.println();
        System.out.println();
//
//
//            System.out.println("Paczkamat name: " + name);
//
//            // window.service.insertPaczkamat(point.name, point.address_details.buildingNumber,
//            //     point.address_details.city, point.address_details.postCode,point.address_details.province,
//            //     point.address_details.street,point.location.latitude,point.location.longitude,point.opening_hours);
//
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

        System.out.println("Street address: " + paczkamat.getStreet());

        data.addPaczkamat(paczkamat);
//        service.insertEntity(paczkamat);

        Collection<Stash> stashes = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Stash stash = new Stash();
            if (i%3 == 0){
                stash.setDimension("SMALL");
            } else if (i%3 == 1) {
                stash.setDimension("MEDIUM");
            } else {
                stash.setDimension("LARGE");
            }
            stash.setPaczkamat(paczkamat);
//            service.insertEntity(stash);
            data.addStash(stash);

            stashes.add(stash);
        }
        paczkamat.setStashes(stashes);

        System.out.println(paczkamat.getPostCode());
        System.out.println(paczkamat.getStashes().size());


    }
}
