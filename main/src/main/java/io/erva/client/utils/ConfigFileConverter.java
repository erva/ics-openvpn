package io.erva.client.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class ConfigFileConverter {

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
}
