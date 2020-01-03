package io.erva.client.utils;

public interface VpnStatusCallback {
    void onVpnStatusChanged(String status);
    default void onVpnDetailedStatusChanged(String detailedStatus){
        //ignore
    }
}
