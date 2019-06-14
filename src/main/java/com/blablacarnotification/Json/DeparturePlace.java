package com.blablacarnotification.Json;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class DeparturePlace {
    @SerializedName("city_name")
    public String city;
    public String address;
}
