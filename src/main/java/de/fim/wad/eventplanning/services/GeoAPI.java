package de.fim.wad.eventplanning.services;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;

public class GeoAPI {

    public void locationToLatLng(String city){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("42899bd499af473fa69a6c33b6d84b5c");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(city);
        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        System.out.println(response);
    }

    public void latLngtoCity(double lat, double lng){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("42899bd499af473fa69a6c33b6d84b5c");

        JOpenCageReverseRequest request = new JOpenCageReverseRequest(lat, lng);
        request.setNoAnnotations(true);

        JOpenCageResponse response = jOpenCageGeocoder.reverse(request);
        System.out.println(response);

    }
}
