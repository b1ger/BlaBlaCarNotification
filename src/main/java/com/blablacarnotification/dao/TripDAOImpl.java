package com.blablacarnotification.dao;

import com.blablacarnotification.Json.TripJsonModel;
import com.blablacarnotification.Model.Trip;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TripDAOImpl implements TripDAO {

    private static EntityManager entityManager;
    private static TripDAO instance;

    private TripDAOImpl() {
        throw new Error("Private constructor");
    }

    private TripDAOImpl(EntityManager entityManager) {
        TripDAOImpl.entityManager = entityManager;
    }

    @Override
    public void save(Long chatId, List<TripJsonModel> trips) {
        // TODO
    }

    @Override
    public List<Trip> getNew(long chatId) {
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
