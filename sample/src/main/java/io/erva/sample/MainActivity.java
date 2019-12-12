package io.erva.sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import de.blinkt.openvpn.VpnProfile;
import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ConnectionStatus;
import de.blinkt.openvpn.core.IOpenVPNServiceInternal;
import de.blinkt.openvpn.core.ProfileManager;
import de.blinkt.openvpn.core.VpnStatus;
import io.erva.client.utils.ConfigFileConverter;
import io.erva.client.utils.VpnHelper;
import io.erva.client.utils.VpnHelperCallback;

import static io.erva.client.utils.VpnHelperCallbackKt.VPN_PERMISSION_REQUEST_CODE;

public class MainActivity extends Activity implements VpnHelperCallback {

    private static final int PICKFILE_RESULT_CODE = 11;
    private String config;
    private TextView configStateTextView;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button startVpnButton;
    private Button stopVpnButton;
    private Button pauseVpnButton;
    private Button resumeVpnButton;
    private VpnProfile mResult;
    private VpnHelper vpnHelper;
    private IOpenVPNServiceInternal vpnController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configStateTextView = findViewById(R.id.tv_config_state);
        findViewById(R.id.btn_chose_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });
        editTextPassword = findViewById(R.id.password);
        editTextLogin = findViewById(R.id.login);
        startVpnButton = findViewById(R.id.btn_start_vpn);
        stopVpnButton = findViewById(R.id.btn_stop_vpn);
        pauseVpnButton = findViewById(R.id.btn_pause_vpn);
        resumeVpnButton = findViewById(R.id.btn_resume_vpn);
        startVpnButton.setOnClickListener(view -> {
            if (!editTextLogin.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty()) {
                startVPN(config, editTextLogin.getText().toString(), editTextPassword.getText().toString());
            } else {
                Toast.makeText(this, "Need to enter password and login", Toast.LENGTH_SHORT).show();
            }
        });

//        startVpnButton.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && data != null) {
            Uri returnUri = data.getData();
            try {
                assert returnUri != null;
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getContentResolver().openInputStream(returnUri))));
                config = ConfigFileConverter.readAndFixConfigFile(br);
                br.close();
                startVpnButton.setVisibility(View.VISIBLE);
                configStateTextView.setText(R.string.config_file_loaded);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("MainActivity", "File not found.");
                configStateTextView.setText("Error while loading config file");
            }
        }
        if (requestCode == VPN_PERMISSION_REQUEST_CODE) {
            vpnPermissionResult(resultCode);
        }
    }

    //todo check
    private void startVPN(String uuid) {
        vpnHelper = new VpnHelper(this, this, uuid);
        vpnHelper.startVpnOrWaitForPermission(this);

    }

    private void startVPN(String config, String username, String password) {
        InputStream is = new ByteArrayInputStream(config.getBytes(StandardCharsets.UTF_8));

        ConfigParser cp = new ConfigParser();
        try {
            InputStreamReader isr = new InputStreamReader(is);

            cp.parseConfig(isr);
            mResult = cp.convertProfile();
            saveProfile(username, password);

            startVPN(mResult.getUUID().toString());

        } catch (IOException | ConfigParser.ConfigParseError e) {
            Log.e(MainActivity.class.getName(), getString(de.blinkt.openvpn.R.string.error_reading_config_file));
        }
    }

    private void saveProfile(String username, String password) {

        ProfileManager vpl = ProfileManager.getInstance(this);
        VpnProfile savedProfile = vpl.getProfileByName(username);
        if (savedProfile != null) {
            mResult = savedProfile;
            return;
        }
        mResult.mName = username;
        mResult.mUsername = username;
        mResult.mPassword = password;

        vpl.addProfile(mResult);
        vpl.saveProfile(this, mResult);
        vpl.saveProfileList(this);
    }

    @Override
    public void vpnServiceBinderReceived(IOpenVPNServiceInternal binder) {
        vpnController = binder;
        stopVpnButton.setVisibility(View.VISIBLE);
//        pauseVpnButton.setVisibility(View.VISIBLE);
//        resumeVpnButton.setVisibility(View.VISIBLE);
        stopVpnButton.setOnClickListener(view -> {
            try {
                vpnController.stopVPN(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        pauseVpnButton.setOnClickListener(view -> {
                    Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
/*
                    try {
                        vpnController.userPause(true);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }*/
                }
        );
        resumeVpnButton.setOnClickListener(view -> {
            Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
                  /*  try {
                        vpnController.userPause(false);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }*/
                }
        );
    }

    @Override
    public void vpnPermissionResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            vpnHelper.startVpnAfterPermission(this);
        } else if (resultCode == RESULT_CANCELED) { // User does not want us to start, so we just vanish
            VpnStatus.updateStateString("USER_VPN_PERMISSION_CANCELLED", "", de.blinkt.openvpn.R.string.state_user_vpn_permission_cancelled,
                    ConnectionStatus.LEVEL_NOTCONNECTED);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                VpnStatus.logError(de.blinkt.openvpn.R.string.nought_alwayson_warning);
        }
    }
}
