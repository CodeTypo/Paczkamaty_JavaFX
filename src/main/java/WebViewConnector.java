import entities.Paczkamat;
import entities.Stash;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.Collection;

public class WebViewConnector {
//    private PaczkamatService service;
//
//    public WebViewConnector(PaczkamatService service) {
//        this.service = service;
//    }

    public void addPaczkamat2(JSObject object, String name, String buildingNumber, String city, String postCode, String province, String street, String latitude, String longitude, String openingHours) {

        System.out.println("Insert paczkamat into table");

        Paczkamat paczkamat = new Paczkamat();
        paczkamat.setCity(city);
        paczkamat.setBuildingNumber(buildingNumber);
        paczkamat.setLatitude(latitude);
        paczkamat.setLongitude(longitude);
        paczkamat.setName(name);
        paczkamat.setOpeningHours(openingHours);
        paczkamat.setProvince(province);
        paczkamat.setPostCode(postCode);

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


            stashes.add(stash);
        }
        paczkamat.setStashes(stashes);
    }

    public void addPaczkamat(JSObject object) {
        String name = (String) object.getMember("name");
        JSObject address_details = (JSObject) object.getMember("address_details");
        String buildingNumber = (String) address_details.getMember("buildingNumber");
        String city = (String) address_details.getMember("city");
        String postCode = (String) address_details.getMember("postCode");
        String province = (String) address_details.getMember("province");
        String street = (String) address_details.getMember("street");
        JSObject location = (JSObject) object.getMember("location");
        String latitude = (String) location.getMember("latitude");
        String longitude = (String) location.getMember("longitude");
        String openingHours = (String) object.getMember("opening_hours");


        System.out.println("Paczkamat name: " + name);

        // window.service.insertPaczkamat(point.name, point.address_details.buildingNumber,
        //     point.address_details.city, point.address_details.postCode,point.address_details.province,
        //     point.address_details.street,point.location.latitude,point.location.longitude,point.opening_hours);

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

            stashes.add(stash);
        }
        paczkamat.setStashes(stashes);

//        service.insertEntity(paczkamat);

    }

}
