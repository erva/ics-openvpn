package io.erva.client.utils;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class VpnStatusHelper {

    private VpnStatusCallback vpnStatusCallback;

    public VpnStatusHelper(Context context, VpnStatusCallback vpnStatusCallback) {
        this.vpnStatusCallback = vpnStatusCallback;
        context.registerReceiver(
                new VpnStatusBroadcastReceiver(),
                new IntentFilter("de.blinkt.openvpn.VPN_STATUS"),
                Manifest.permission.ACCESS_NETWORK_STATE,
                null);
    }

    private class VpnStatusBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("status");
            String detailedStatus = intent.getStringExtra("detailstatus");
            vpnStatusCallback.onVpnStatusChanged(status);
            vpnStatusCallback.onVpnStatusChanged(detailedStatus);
        }
    }


}


