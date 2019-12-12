package io.erva.client.utils

import android.app.Activity
import android.content.*
import android.net.VpnService
import android.os.Build
import android.os.IBinder
import de.blinkt.openvpn.VpnProfile
import de.blinkt.openvpn.core.*
import java.io.IOException

class VpnHelper(activity: Activity, val callback: VpnHelperCallback, shortcutUUID: String) {

    companion object {

        const val CLEARLOG = "clearlogconnect"
    }

    private val mSelectedProfile: VpnProfile
    private var serviceCommander: IOpenVPNServiceInternal? = null
    private var mCmfixed = false
    private var isWaitingForPermission = false

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(className: ComponentName?) {
            className
        }

        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            if (className.className == OpenVPNService::class.java.name) {
                serviceCommander = IOpenVPNServiceInternal.Stub.asInterface(binder);
                callback.vpnServiceBinderReceived(serviceCommander!!)
            } else {
                serviceCommander = null
            }
        }
    }

    init {
        if (Preferences.getDefaultSharedPreferences(activity).getBoolean(CLEARLOG, true))
            VpnStatus.clearLog()

        val profileToConnect = ProfileManager.get(activity, shortcutUUID)
        if (profileToConnect == null) {
            VpnStatus.logError(de.blinkt.openvpn.R.string.shortcut_profile_notfound)
            throw IllegalArgumentException("VPN profile must be saved")
        } else {
            mSelectedProfile = profileToConnect
        }
    }

    fun startVpnOrWaitForPermission(activity: Activity) {
        val intent = VpnService.prepare(activity)
        // Check if we want to fix /dev/tun
        val prefs = Preferences.getDefaultSharedPreferences(activity)
        val usecm9fix = prefs.getBoolean("useCM9Fix", false)
        val loadTunModule = prefs.getBoolean("loadTunModule", false)
        if (loadTunModule) execeuteSUcmd("insmod /system/lib/modules/tun.ko")
        if (usecm9fix && !mCmfixed) {
            execeuteSUcmd("chown system /dev/tun")
        }
        if (intent != null) {
            VpnStatus.updateStateString("USER_VPN_PERMISSION", "", de.blinkt.openvpn.R.string.state_user_vpn_permission,
                ConnectionStatus.LEVEL_WAITING_FOR_USER_INPUT);
            // Start the query
            try {
                activity.startActivityForResult(intent, VPN_PERMISSION_REQUEST_CODE)
                isWaitingForPermission = true
            } catch (e: ActivityNotFoundException) {
                // Shame on you Sony! At least one user reported that
                // an official Sony Xperia Arc S image triggers this exception
                VpnStatus.logError(de.blinkt.openvpn.R.string.no_vpn_support_image)
            }
        } else {
            startVPNConnection(activity);
        }
    }

    fun startVpnAfterPermission(context: Context) {
        if (isWaitingForPermission) {
            isWaitingForPermission = false
            startVPNConnection(context)
        }
    }

    private fun execeuteSUcmd(command: String) {
        try {
            val pb = ProcessBuilder("su", "-c", command)
            val p = pb.start()
            val ret = p.waitFor()
            if (ret == 0) mCmfixed = true
        } catch (e: InterruptedException) {
            VpnStatus.logException("SU command", e)
        } catch (e: IOException) {
            VpnStatus.logException("SU command", e)
        }
    }

    private fun startVPNConnection(context: Context) {
        ProfileManager.updateLRU(context, mSelectedProfile)
        val startVPN: Intent = mSelectedProfile.prepareStartService(context)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) context.startForegroundService(startVPN) else context.startService(startVPN)
        val intent = Intent(context, OpenVPNService::class.java)
        intent.action = OpenVPNService.START_SERVICE
        val result = context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}