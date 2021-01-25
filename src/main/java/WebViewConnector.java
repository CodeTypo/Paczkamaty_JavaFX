import entities.Paczkamat;
import entities.Stash;
import netscape.javascript.JSObject;

import java.util.ArrayList;
import java.util.Collection;

public class WebViewConnector {
    public void addPaczkamat2(JSObject object,String name, String buildingNumber, String city, String postCode,String province,String street,String latitude,String longitude,String openingHours) {

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

        System.out.println("Paczkamat name: " + name);

//        Paczkamat paczkamat = new Paczkamat();
//        paczkamat.setCity(city);
//        paczkamat.setBuildingNumber(buildingNumber);
//        paczkamat.setLatitude(latitude);
//        paczkamat.setLongitude(longitude);
//        paczkamat.setName(name);
//        paczkamat.setOpeningHours(openingHours);
//        paczkamat.setProvince(province);
//        paczkamat.setPostCode(postCode);
//
//        Collection<Stash> stashes = new ArrayList<>();
//        for (int i = 0; i < 14; i++) {
//            Stash stash = new Stash();
//            if (i%3 == 0){
//                stash.setDimension("SMALL");
//            } else if (i%3 == 1) {
//                stash.setDimension("MEDIUM");
//            } else {
//                stash.setDimension("LARGE");
//            }
//
//
//            stashes.add(stash);
//        }
//        paczkamat.setStashes(stashes);
    }
}
