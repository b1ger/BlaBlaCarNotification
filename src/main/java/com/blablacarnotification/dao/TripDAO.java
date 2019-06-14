package com.blablacarnotification.dao;

import com.blablacarnotification.Json.TripJsonModel;
import com.blablacarnotification.Model.Trip;

import java.util.List;

public interface TripDAO {

    void save(Long chatId, List<TripJsonModel> trips);
    List<Trip> getNew(long chatId);
}
