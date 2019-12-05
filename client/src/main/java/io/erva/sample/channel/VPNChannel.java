package io.erva.sample.channel;

public interface VPNChannel {


    /**
     * available VPN Status
     */
    enum OpenVPNStatus { connected, disconnected, invalid }

    /**
     * Check if OVPN configuration settled
     * @return true if has configuration, false if not
     */
    boolean isConfigurationExist();

    /**
     * Check connection status
     *
     * @return one of statuses form OpenVPNStatus
     */
    String openVPNStatus();

    /**
     * Starts VPN session and save credentials for further connections
     *
     * @param username - login for vpn connection
     * @param password - password for vpn connection
     * @return true if vpn session started
     */
    boolean setupAndStartVPN(String username, String password);

    /**
     * Starts VPN connection with stored credentials
     * @return true if vpn session started
     */
    boolean startVPN();

    /**
     * Stop vpn without closing session
     */
    void stopVPN();

    /**
     * Stop Vpn and close session and remove credentials
     * @return true if vpn session closed successfully and credentials removed
     */
    boolean closeVPN();

    /**
     * Called when VPN status changes
     *
     * @return one of statuses form OpenVPNStatus
     */
    String openVPNStatusDidChanged();
}
