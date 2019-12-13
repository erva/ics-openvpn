package io.erva.client.utils

import de.blinkt.openvpn.core.IOpenVPNServiceInternal

const val VPN_PERMISSION_REQUEST_CODE = 12

interface VpnHelperCallback {
    fun vpnServiceBinderReceived(binder: IOpenVPNServiceInternal)
    fun vpnPermissionResult(resultCode: Int)
}