package com.blablacarnotification.Dao;

import com.blablacarnotification.Json.TripJsonModel;
import com.blablacarnotification.Model.Trip;
import com.blablacarnotification.Voyage.TripManager;

import java.util.List;

public interface TripDAO {

    void save(Long chatId, List<TripJsonModel> trips, TripManager tripManager);
    List<Trip> getNew(Long chatId, Long lastId);
    void clear(Long chatId);
}
