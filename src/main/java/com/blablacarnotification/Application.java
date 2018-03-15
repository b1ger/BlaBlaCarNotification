package com.blablacarnotification;

import com.blablacarnotification.Voyage.Voyage;

public class Application {
    public static void main(String[] args) {
        Thread thread = new Thread(new Voyage());
        thread.start();
    }
}
