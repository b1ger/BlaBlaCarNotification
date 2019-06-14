package com.blablacarnotification.Json;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class TripJsonModel {

    @SerializedName("permanent_id") public String id;
    @SerializedName("departure_date") public String departureDate;
    @SerializedName("departure_place") public DeparturePlace departurePlace;
    @SerializedName("arrival_place") public ArrivalPoint arrivalPoint;
    @SerializedName("price") public Price price;
    public Car car;
    public String seats;
    @SerializedName("seats_left") public String seatsLeft;
    @SerializedName("links") public Link link;

}
