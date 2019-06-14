package com.blablacarnotification.Voyage;

import com.blablacarnotification.dao.TripDAO;

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
                // TODO
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
