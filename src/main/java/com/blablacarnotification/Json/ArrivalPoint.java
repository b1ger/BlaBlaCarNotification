package com.blablacarnotification.Json;

import com.google.gson.annotations.SerializedName;

public class ArrivalPoint {
    @SerializedName("city_name") public String city;
    public String address;
    @SerializedName("full_name") public String fullName;
}
