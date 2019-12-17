package io.erva.client.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import de.blinkt.openvpn.VpnProfile;
import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ProfileManager;

public class ConfigHelper {

  /**
   * Current VPN connection implementation doesn't support TCP, so remove it from config.
   * TODO this is temporary 5.12.2019
   * @throws IOException
   */
  public static String readAndFixConfigFile(BufferedReader reader) throws IOException {
    StringBuilder result = new StringBuilder();
    String line;
    StringBuilder tempLines = new StringBuilder();
    boolean isConnectionBlock = false;
    boolean isContainsUpd = false;

    while ((line = reader.readLine()) != null) {
      if (line.contains("<connection>")) {
        isConnectionBlock = true;
      }
      if (isConnectionBlock) {
        tempLines.append(line);
        tempLines.append('\n');
        if (line.contains("</connection>")) {
          isConnectionBlock = false;

          if (!tempLines.toString().matches("(?s)(.*)remote(?s)(.*)tcp(?s)(.*)")) {
            result.append(tempLines);
          }
          tempLines = new StringBuilder();
        }
        continue;
      }
      result.append(line);
      result.append('\n');
    }

    return result.toString();
  }

  public static VpnProfile getProfile(Context context, String config, String username, String password){
      InputStream inputStream = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));

    VpnProfile result = null;
      ConfigParser cp = new ConfigParser();
        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            cp.parseConfig(isr);
            result = cp.convertProfile();
            ProfileManager vpl = ProfileManager.getInstance(context);
            VpnProfile savedProfile = vpl.getProfileByName(username);
            if (savedProfile != null) {
                vpl.removeProfile(context, savedProfile);
            }
            result.mName = username;
            result.mUsername = username;
            result.mPassword = password;
            vpl.addProfile(result);
            vpl.saveProfile(context, result);
            vpl.saveProfileList(context);
        } catch ( IOException | ConfigParser.ConfigParseError e) {
            e.printStackTrace();
        }
      return result;
    }
}
