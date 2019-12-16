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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import io.erva.client.utils.ConfigHelper;
import io.erva.client.utils.VPNController;
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
    private VpnHelper vpnHelper;
    private VPNController vpnController;


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
                vpnHelper.startVpnOrWaitForPermission(this);
                vpnHelper.startVPN(MainActivity.this, config, editTextLogin.getText().toString(), editTextPassword.getText().toString());
            } else {
                Toast.makeText(this, "Need to enter password and login", Toast.LENGTH_SHORT).show();
            }
        });
        vpnHelper = new VpnHelper(this);
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
                config = ConfigHelper.readAndFixConfigFile(br);
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

    @Override
    public void vpnServiceBinderReceived(VPNController binder) {
        vpnController = binder;
        stopVpnButton.setVisibility(View.VISIBLE);
        stopVpnButton.setOnClickListener(view -> {
            try {
                vpnController.stopVPN(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void vpnPermissionResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            vpnHelper.startVpnAfterPermission(this);
        } else if (resultCode == RESULT_CANCELED) { // User does not want us to start, so we just vanish
            Toast.makeText(this, "No permission for vpn", Toast.LENGTH_SHORT).show();
        }
    }
}
