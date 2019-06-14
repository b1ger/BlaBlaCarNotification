package com.blablacarnotification.Voyage;

import com.blablacarnotification.Model.Trip;
import com.blablacarnotification.dao.TripDAO;

import java.util.List;

public class TripManager implements Runnable {

    private TripDAO tripDAO;
    private Voyage voyage;
    private boolean newAvailable = false;

    public TripManager(TripDAO tripDAO, Voyage voyage) {
        this.tripDAO = tripDAO;
        this.voyage = voyage;
    }

    @Override
    public void run() {
        while (voyage.isRunning()) {
            if (newAvailable) {
                List<Trip> availableTrips = tripDAO.getNew(voyage.getChatId());
                for (Trip trip : availableTrips) {
                    voyage.send(trip.toString());
                }
                newAvailable = false;
            }
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException ex) {
                voyage.stop();
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setAvailable() {
        this.newAvailable = true;
    }
}
