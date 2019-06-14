package com.blablacarnotification.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "cartrips")
@Entity
@Setter
@Getter
@ToString
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permanent_id")
    private String tripId;

    @Column(name = "departure_date")
    private String date;

    @Column(name = "departure_city_name")
    private String departureCity;

    @Column(name = "departure_address")
    private String departureAddress;

    @Column(name = "arrival_city_name")
    private String arrivalCity;

    @Column(name = "arrival_address")
    private String arrivalAddress;

    @Column(name = "price")
    private String priceValue;

    @Column(name = "currency")
    private String currency;

    @Column(name = "car")
    private String car;

    @Column(name = "seats")
    private String seats;

    @Column(name = "seats_left")
    private String seatsLeft;

    @Column(name = "link")
    private String link;

    public Trip() {}
}

