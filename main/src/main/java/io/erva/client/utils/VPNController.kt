package io.erva.client.utils

import android.os.RemoteException
import de.blinkt.openvpn.core.IOpenVPNServiceInternal

class VPNController(private val field: IOpenVPNServiceInternal) {
    @Throws(RemoteException::class)
    fun stopVPN(replaceConnection: Boolean): Boolean {
        return field.stopVPN(replaceConnection)
    }
}