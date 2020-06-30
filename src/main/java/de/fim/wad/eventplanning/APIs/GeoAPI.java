package de.fim.wad.eventplanning.APIs;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;

public class GeoAPI {

    public static double[] locationToLatLng(String city){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("42899bd499af473fa69a6c33b6d84b5c");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(city);
        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();

        System.out.println(firstResultLatLng.getLat());
        System.out.println(firstResultLatLng.getLng());
        return new double[]{firstResultLatLng.getLat(), firstResultLatLng.getLng()};
    }

    public static String latLngToCity(double lat, double lng){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("42899bd499af473fa69a6c33b6d84b5c");

        JOpenCageReverseRequest request = new JOpenCageReverseRequest(lat, lng);
        request.setNoAnnotations(true);

        JOpenCageResponse response = jOpenCageGeocoder.reverse(request);
        String formattedAddress = response.getResults().get(0).getFormatted();
        String city = response.getResults().get(0).getComponents().getCity();
        System.out.println(formattedAddress);
        System.out.println(city);

        return city;
    }
}
