package com.blablacarnotification.Json;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Link {
    @SerializedName("_front")
    public String front;
}
