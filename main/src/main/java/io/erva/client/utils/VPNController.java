package io.erva.client.utils;

import android.os.RemoteException;

import de.blinkt.openvpn.core.IOpenVPNServiceInternal;

public class VPNController {
    private IOpenVPNServiceInternal field;

    public VPNController(IOpenVPNServiceInternal field) {
        this.field = field;
    }

    public boolean stopVPN(boolean replaceConnection) throws RemoteException {
        return field.stopVPN(replaceConnection);
    }
}
