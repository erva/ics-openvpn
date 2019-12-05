package io.erva.sample.channel;

public interface VPNChannel {
    String CHANNEL = "com.sigmasoftware.openVPN";

    boolean isConfigurationExist();

    String openVPNStatus();

    boolean setupAndStartVPN(String username, String password);

    boolean startVPN();

    void stopVPN();

    boolean closeVPN();

    String openVPNStatusDidChanged();
}
