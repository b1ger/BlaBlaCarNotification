package com.blablacarnotification.Model;

import com.blablacarnotification.Json.TripJsonModel;

public class Converter {

    public static Trip jsonToModel(TripJsonModel source) {
        final Trip trip = new Trip();
        trip.setTripId(source.getId());
        trip.setDate(source.getDepartureDate());
        if (source.getDeparturePlace() != null) {
            trip.setDepartureCity(source.getDeparturePlace().getCity());
            trip.setDepartureAddress(source.getDeparturePlace().getAddress());
        }
        if (source.getArrivalPoint() != null) {
            trip.setArrivalCity(source.getArrivalPoint().getCity());
            trip.setArrivalAddress(source.getArrivalPoint().getAddress());
        }
        if (source.getPrice() != null) {
            trip.setCurrency(source.getPrice().getCurrency());
            trip.setPriceValue(source.getPrice().getValue());
        }
        if (source.getCar() != null) {
            trip.setCar(source.getCar().getMake() + " " + source.getCar().getModel());
        }
        trip.setSeats(source.getSeats());
        trip.setSeatsLeft(source.getSeatsLeft());
        if (source.getLink() != null) {
            trip.setLink(source.getLink().getFront());
        }
        return trip;
    }
}
