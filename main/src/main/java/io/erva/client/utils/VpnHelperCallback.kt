package io.erva.client.utils

const val VPN_PERMISSION_REQUEST_CODE = 12

interface VpnHelperCallback {
    fun vpnServiceBinderReceived(binder: VPNController)
    fun vpnPermissionResult(resultCode: Int)
}