package io.erva.client;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import de.blinkt.openvpn.VpnProfile;
import de.blinkt.openvpn.core.ConfigParser;
import io.erva.client.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

public class Client {

  private VpnProfile mResult;
  private transient List<String> mPathsegments;
  private String mEmbeddedPwFile;
  private String mAliasName = null;


  public void main() {

  }

  private void parse(String config) {
    doImport(new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8)));
  }

  public void doImport(InputStream is) {
    ConfigParser cp = new ConfigParser();
    try {
      InputStreamReader isr = new InputStreamReader(is);

      cp.parseConfig(isr);
      mResult = cp.convertProfile();
      embedFiles(cp);
      return;

    } catch (IOException | ConfigParser.ConfigParseError e) {
      log(de.blinkt.openvpn.R.string.error_reading_config_file);
      Log.d("pajero", e.getLocalizedMessage());
    }
    mResult = null;

  }

  void embedFiles(ConfigParser cp) {
    // This where I would like to have a c++ style
    // void embedFile(std::string & option)

    if (mResult.mPKCS12Filename != null) {
      File pkcs12file = findFileRaw(mResult.mPKCS12Filename);
      if (pkcs12file != null) {
        mAliasName = pkcs12file.getName().replace(".p12", "");
      } else {
        mAliasName = "Imported PKCS12";
      }
    }


    mResult.mCaFilename = embedFile(mResult.mCaFilename, Utils.FileType.CA_CERTIFICATE, false);
    mResult.mClientCertFilename = embedFile(mResult.mClientCertFilename, Utils.FileType.CLIENT_CERTIFICATE, false);
    mResult.mClientKeyFilename = embedFile(mResult.mClientKeyFilename, Utils.FileType.KEYFILE, false);
    mResult.mTLSAuthFilename = embedFile(mResult.mTLSAuthFilename, Utils.FileType.TLS_AUTH_FILE, false);
    mResult.mPKCS12Filename = embedFile(mResult.mPKCS12Filename, Utils.FileType.PKCS12, false);
    mResult.mCrlFilename = embedFile(mResult.mCrlFilename, Utils.FileType.CRL_FILE, true);
    if (cp != null) {
      mEmbeddedPwFile = cp.getAuthUserPassFile();
      mEmbeddedPwFile = embedFile(cp.getAuthUserPassFile(), Utils.FileType.USERPW_FILE, false);
    }

  }

  private String embedFile(String filename, Utils.FileType type, boolean onlyFindFileAndNullonNotFound) {
    if (filename == null)
      return null;

    // Already embedded, nothing to do
    if (VpnProfile.isEmbedded(filename))
      return filename;

    File possibleFile = findFile(filename, type);
    if (possibleFile == null)
      if (onlyFindFileAndNullonNotFound)
        return null;
      else
        return filename;
    else if (onlyFindFileAndNullonNotFound)
      return possibleFile.getAbsolutePath();
    else
      return readFileContent(possibleFile, type == Utils.FileType.PKCS12);

  }

  private File findFile(String filename, Utils.FileType fileType) {
    File foundfile = findFileRaw(filename);

    if (foundfile == null && filename != null && !filename.equals("")) {
      log(de.blinkt.openvpn.R.string.import_could_not_open, filename);
    }

    return foundfile;
  }

  private File findFileRaw(String filename) {
    if (filename == null || filename.equals(""))
      return null;

    // Try diffent path relative to /mnt/sdcard
    File sdcard = Environment.getExternalStorageDirectory();
    File root = new File("/");

    HashSet<File> dirlist = new HashSet<>();

    for (int i = mPathsegments.size() - 1; i >= 0; i--) {
      String path = "";
      for (int j = 0; j <= i; j++) {
        path += "/" + mPathsegments.get(j);
      }
      // Do a little hackish dance for the Android File Importer
      // /document/primary:ovpn/openvpn-imt.conf


      if (path.indexOf(':') != -1 && path.lastIndexOf('/') > path.indexOf(':')) {
        String possibleDir = path.substring(path.indexOf(':') + 1, path.length());
        // Unquote chars in the  path
        try {
          possibleDir = URLDecoder.decode(possibleDir, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {}

        possibleDir = possibleDir.substring(0, possibleDir.lastIndexOf('/'));




        dirlist.add(new File(sdcard, possibleDir));

      }
      dirlist.add(new File(path));


    }
    dirlist.add(sdcard);
    dirlist.add(root);


    String[] fileparts = filename.split("/");
    for (File rootdir : dirlist) {
      String suffix = "";
      for (int i = fileparts.length - 1; i >= 0; i--) {
        if (i == fileparts.length - 1)
          suffix = fileparts[i];
        else
          suffix = fileparts[i] + "/" + suffix;

        File possibleFile = new File(rootdir, suffix);
        if (possibleFile.canRead())
          return possibleFile;

      }
    }
    return null;
  }

  String readFileContent(File possibleFile, boolean base64encode) {
    byte[] filedata;
    try {
      filedata = readBytesFromFile(possibleFile);
    } catch (IOException e) {
      Log.d("pajero", e.getLocalizedMessage());
      return null;
    }

    String data;
    if (base64encode) {
      data = Base64.encodeToString(filedata, Base64.DEFAULT);
    } else {
      data = new String(filedata);

    }

    return VpnProfile.DISPLAYNAME_TAG + possibleFile.getName() + VpnProfile.INLINE_TAG + data;

  }

  private byte[] readBytesFromFile(File file) throws IOException {
    InputStream input = new FileInputStream(file);

    long len = file.length();
    if (len > VpnProfile.MAX_EMBED_FILE_SIZE)
      throw new IOException("File size of file to import too large.");

    // Create the byte array to hold the data
    byte[] bytes = new byte[(int) len];

    // Read in the bytes
    int offset = 0;
    int bytesRead;
    while (offset < bytes.length
        && (bytesRead = input.read(bytes, offset, bytes.length - offset)) >= 0) {
      offset += bytesRead;
    }

    input.close();
    return bytes;
  }


  private void log(int ressourceId, Object... formatArgs) {
    Log.d("pajero", ressourceId+""/*getString(ressourceId, formatArgs)*/);
  }
}
