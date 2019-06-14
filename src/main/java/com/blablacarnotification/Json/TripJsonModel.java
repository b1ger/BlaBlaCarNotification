package com.blablacarnotification.Json;

import com.google.gson.annotations.SerializedName;

public class TripJsonModel {

    @SerializedName("permanent_id") public String id;
    @SerializedName("departure_date") public String departureDate;
    @SerializedName("departure_place") public DeparturePlace departurePlace;
    @SerializedName("arrival_meeting_point") public ArrivalPoint arrivalPoint;
    @SerializedName("price") public Price price;
    public Car car;
    public String seats;
    @SerializedName("seats_left") public String seatsLeft;
    @SerializedName("links") public Link link;


    @Override
    public String toString() {
        String details = "Trip id: " + id + "\nDate: " + departureDate + "\n";
        if (departurePlace != null) {
            details +=  "Departure:\n\tCity: " + departurePlace.city + "\n\tAddress: " + departurePlace.address + "\n";
        }
        if (arrivalPoint != null) {
            details += "Arrival point:\n\tCity: " + arrivalPoint.city + "\n\tAddress: " + arrivalPoint.address + "," + arrivalPoint.fullName + "\n";
        } else {
            details += "Arrival point:\n\tCity: Kaniv\n";
        }
        if (car != null) {
            details += "Car: " + car.make + " " + car.model + "\n";
        }
        details += "Seats: " + seats +"\nSeats left: " + seatsLeft +"\nPrice: " + price.value + price.currency + "\nLink: " + link.front;

        return details;
    }
}
