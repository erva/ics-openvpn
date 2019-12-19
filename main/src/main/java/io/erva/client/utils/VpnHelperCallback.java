package io.erva.client.utils;

public interface VpnHelperCallback {
    int VPN_PERMISSION_REQUEST_CODE = 12;

    void vpnServiceBinderReceived(VPNController binder);

    void vpnPermissionResult(int resultCode);
}
