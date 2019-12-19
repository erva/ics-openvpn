package io.erva.client.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.VpnService;
import android.os.Build.VERSION;
import android.os.IBinder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import de.blinkt.openvpn.R.string;
import de.blinkt.openvpn.VpnProfile;
import de.blinkt.openvpn.core.ConnectionStatus;
import de.blinkt.openvpn.core.IOpenVPNServiceInternal;
import de.blinkt.openvpn.core.OpenVPNService;
import de.blinkt.openvpn.core.Preferences;
import de.blinkt.openvpn.core.ProfileManager;
import de.blinkt.openvpn.core.VpnStatus;


@SuppressWarnings("unused")
public final class VpnHelper {

    public VpnHelper(@NotNull VpnHelperCallback callback) {
        this.callback = callback;
        this.connection = new ServiceConnection() {
            public void onServiceDisconnected(@Nullable ComponentName className) {
            }

            public void onServiceConnected(@NotNull ComponentName className, @NotNull IBinder binder) {
                if (className.getClassName().equals(OpenVPNService.class.getName())) {
                    serviceCommander = IOpenVPNServiceInternal.Stub.asInterface(binder);
                    callback.vpnServiceBinderReceived(new VPNController(serviceCommander));
                } else {
                    serviceCommander = null;
                }
            }
        };
    }

    private VpnProfile mSelectedProfile;
    private IOpenVPNServiceInternal serviceCommander;
    private boolean mCmfixed;
    private boolean isWaitingForPermission;
    private final ServiceConnection connection;
    @NotNull
    private final VpnHelperCallback callback;

    private void startVpnOrWaitForPermission(Activity activity) {
        Intent intent = VpnService.prepare(activity);
        SharedPreferences prefs = Preferences.getDefaultSharedPreferences(activity);
        boolean usecm9fix = prefs.getBoolean("useCM9Fix", false);
        boolean loadTunModule = prefs.getBoolean("loadTunModule", false);
        if (loadTunModule) {
            this.execeuteSUcmd("insmod /system/lib/modules/tun.ko");
        }

        if (usecm9fix && !this.mCmfixed) {
            this.execeuteSUcmd("chown system /dev/tun");
        }

        if (intent != null) {
            VpnStatus.updateStateString("USER_VPN_PERMISSION", "", string.state_user_vpn_permission, ConnectionStatus.LEVEL_WAITING_FOR_USER_INPUT);

            try {
                activity.startActivityForResult(intent, 12);
                this.isWaitingForPermission = true;
            } catch (ActivityNotFoundException var7) {
                VpnStatus.logError(string.no_vpn_support_image);
            }
        } else {
            this.startVPNConnection(activity);
        }

    }

    public final void startVpnAfterPermission(@NotNull Context context) {
        if (this.isWaitingForPermission) {
            this.isWaitingForPermission = false;
            this.startVPNConnection(context);
        }

    }

    private void execeuteSUcmd(String command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(new String[]{"su", "-c", command});
            Process p = pb.start();
            int ret = p.waitFor();
            if (ret == 0) {
                this.mCmfixed = true;
            }
        } catch (InterruptedException | IOException e) {
            VpnStatus.logException("SU command", e);
        }

    }

    private void startVPNConnection(Context context) {
        if (this.mSelectedProfile == null) {
            throw new IllegalArgumentException("Profile must be set");
        } else {
            ProfileManager.updateLRU(context, this.mSelectedProfile);
            VpnProfile selectedProfile = this.mSelectedProfile;
            Intent startVPN = selectedProfile != null ? selectedProfile.prepareStartService(context) : null;
            if (VERSION.SDK_INT >= 26) {
                context.startForegroundService(startVPN);
            } else {
                context.startService(startVPN);
            }

            Intent intent = new Intent(context, OpenVPNService.class);
            intent.setAction("de.blinkt.openvpn.START_SERVICE");
            context.bindService(intent, this.connection, Context.BIND_AUTO_CREATE);
        }
    }

    public final void startVPN(@NotNull Activity activity, @NotNull String config, @NotNull String username, @NotNull String password) {
        this.mSelectedProfile = ConfigHelper.getProfile(activity, config, username, password);
        this.startVpnOrWaitForPermission(activity);
    }

    @NotNull
    public final VpnHelperCallback getCallback() {
        return this.callback;
    }


}
