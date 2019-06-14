package com.blablacarnotification.dao;

import com.blablacarnotification.Json.TripJsonModel;
import com.blablacarnotification.Model.Trip;
import com.blablacarnotification.Voyage.TripManager;

import java.util.List;

public interface TripDAO {

    void save(Long chatId, List<TripJsonModel> trips, TripManager tripManager);
    List<Trip> getNew(long chatId);
}
