package com.blablacarnotification.dao;

import com.blablacarnotification.Json.TripJsonModel;
import com.blablacarnotification.Model.Converter;
import com.blablacarnotification.Model.Trip;
import com.blablacarnotification.Voyage.TripManager;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripDAOImpl implements TripDAO {

    private static EntityManager entityManager;
    private static TripDAO instance;

    private Map<Long, Long> chatIdToLastTripId;

    private TripDAOImpl() {
        throw new Error("Private constructor");
    }

    private TripDAOImpl(EntityManager entityManager) {
        TripDAOImpl.entityManager = entityManager;
        this.chatIdToLastTripId = new HashMap<>();
    }

    @Override
    public synchronized void save(Long chatId, List<TripJsonModel> trips, TripManager tripManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Long currentTripId = chatIdToLastTripId.get(chatId);
            transaction.begin();
            Query query = entityManager.createQuery("select t.tripId from Trip t", String.class);
            List<String> tripIds = query.getResultList();
            for (TripJsonModel jsonModel : trips) {
                Trip trip = Converter.jsonToModel(jsonModel);
                if (tripIds.contains(trip.getTripId())) {
                    query = entityManager.createQuery("select t from Trip t where t.tripId=?1", Trip.class);
                    query.setParameter(1, trip.getTripId());
                    Trip detached = (Trip) query.getResultList().get(0);
                    detached.setSeatsLeft(trip.getSeatsLeft());
                    detached.setDate(trip.getDate());
                    detached.setArrivalCity(trip.getArrivalCity());
                    detached.setArrivalAddress(trip.getArrivalAddress());
                    entityManager.merge(detached);
                } else {
                    entityManager.persist(trip);
                    chatIdToLastTripId.put(chatId, trip.getId());
                }
            }
            entityManager.flush();
            if (currentTripId == null ||
                    currentTripId < chatIdToLastTripId.get(chatId)) {
                tripManager.setAvailable();
            }
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public synchronized List<Trip> getNew(long chatId) {
        // TODO
        return null;
    }

    public static synchronized TripDAO getInstance() {
        if (instance == null) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("CarTrips");
            entityManager = managerFactory.createEntityManager();
            instance = new TripDAOImpl(entityManager);
        }
        return instance;
    }
}
