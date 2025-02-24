package io.github.boodyahmedhamdy.mealano.utils.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkMonitor {

    ConnectivityManager connectivityManager;
    private static NetworkMonitor instance;
    private NetworkMonitor(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    public static NetworkMonitor getInstance(ConnectivityManager connectivityManager) {
        if(instance == null) {
            instance = new NetworkMonitor(connectivityManager);
        }
        return instance;
    }


    public Boolean isConnected() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
