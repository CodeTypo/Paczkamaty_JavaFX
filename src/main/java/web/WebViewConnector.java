package web;

import entities.Paczkamat;
import entities.Stash;
import netscape.javascript.JSObject;
import services.DataSource;

import java.util.ArrayList;
import java.util.Collection;

public class WebViewConnector {
//    private DataSource data;

    public WebViewConnector() {
    }

    public void log(String text)
    {
        System.out.println(text);
    }

    public void addPaczkamat(JSObject object) {
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

        DataSource.addPaczkamat(paczkamat);

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
            DataSource.addStash(stash);

            stashes.add(stash);
        }
        paczkamat.setStashes(stashes);

        System.out.println(paczkamat.getPostCode());
        System.out.println(paczkamat.getStashes().size());


    }
}
